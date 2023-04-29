package src;

import src.menus.Builder;
import src.menus.Button;
import src.menus.Menu;
import src.menus.MenuEnum;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class WebShop {
    Dictionary<MenuEnum, Menu> menus = new Hashtable<>();

//    Dictionary<String,Runnable> actions = new Hashtable<>();
    Hashtable<String,Runnable> actions = new Hashtable<>();
    Database database = new Database();
    ArrayList<Product> products;
    ArrayList<Customer> customers;
    String username;
    String password;
    Customer currentCustomer; //= new Customer("Maryam","","","","",0,"","");

    Customer foundCustomer=null;
    Scanner scanner = new Scanner(System.in);
    Menu currentMenu;

    private static WebShop webShopInstance = null;
    public static WebShop getInstance(){
        if(webShopInstance == null){
            webShopInstance = new WebShop();
        }
        return webShopInstance;
    }
    private WebShop() {
        products = database.GetProducts();
        customers = database.GetCustomers();
        List<Button> mainMenuBtns = Arrays.asList(new Button("See Wares", null, this::alwaysEnabled, MenuEnum.WARE), new Button("Customer Info", null, this::logoutEnabled, MenuEnum.COSTUMER),new Button("Customer Info", this::ShowLoginError, this::loginEnabled, MenuEnum.MAIN), new Button("Login", null, this::loginEnabled, MenuEnum.LOGIN), new Button("Logout", this::printLogoutMessage, this::logoutEnabled, MenuEnum.MAIN));
        Menu mainMenu = (new Builder()).header("What would you like to do?").addBtn(mainMenuBtns).backTo(MenuEnum.MAIN).build();

        List<Button> customerMenuBtns = Arrays.asList(new Button("See your orders", this::PrintCustomerOrders, this::alwaysEnabled, MenuEnum.COSTUMER), new Button("See your info", this::PrintCustomerInfo, this::alwaysEnabled, MenuEnum.COSTUMER), new Button("Add funds", this::GetFundAmount, this::alwaysEnabled, MenuEnum.COSTUMER)

        );
        Menu customerMenu = (new Builder()).header("What would you like to do?").addBtn(customerMenuBtns).backTo(MenuEnum.MAIN).build();

        List<Button> loginMenuBtns = Arrays.asList(new Button("Set Username", this::InputUsename, this::alwaysEnabled, MenuEnum.LOGIN), new Button("Set Password", this::GetPassword, this::alwaysEnabled, MenuEnum.LOGIN), new Button("Login", this::ShowLoggedInUsername, this::foundUser, MenuEnum.MAIN), new Button("Login", this::ShowIncompleteData, this::usernamePasswordCompelte, MenuEnum.LOGIN), new Button("Login", this::ShowInvalidAuthurization, () -> !this.usernamePasswordCompelte() && !this.foundUser(), MenuEnum.MAIN), new Button("Register", this::RegisterInfo, this::alwaysEnabled, MenuEnum.MAIN));
        Menu loginMenu = (new Builder()).header("Please submit username and password.").addBtn(loginMenuBtns).backTo(MenuEnum.MAIN).build();

        List<Button> wareMenuBtns = Arrays.asList(new Button("See all wares", this::ShowProductInfo, this::alwaysEnabled, MenuEnum.WARE), new Button("Purchase a ware", null, this::logoutEnabled, MenuEnum.PURCHASE), new Button("Purchase a ware", this::ShowLoginError, this::loginEnabled, MenuEnum.WARE), new Button("Sort wares", null, this::alwaysEnabled, MenuEnum.SORT), new Button("Login", null, this::loginEnabled, MenuEnum.LOGIN), new Button("Logout", this::ShowUsernameLogout, this::logoutEnabled, MenuEnum.WARE));
        Menu wareMenu = (new Builder()).header("What would you like to do?").addBtn(wareMenuBtns).backTo(MenuEnum.MAIN).build();


        List<Button> sortMenuBtns = Arrays.asList(new Button("Sort by name, descending", this::SortByNameIn, this::alwaysEnabled, MenuEnum.WARE), new Button("Sort by name, ascending", this::SortByNameDe, this::alwaysEnabled, MenuEnum.WARE), new Button("Sort by price, descending", this::SortByPriceIn, this::alwaysEnabled, MenuEnum.WARE), new Button("Sort by price, ascending", this::SortByPriceDe, this::alwaysEnabled, MenuEnum.WARE)

        );
        Menu sortMenu = (new Builder()).header("How would you like to sort them?").addBtn(sortMenuBtns).backTo(MenuEnum.WARE).build();

        List<Button> purchaseMenuBtns = products.stream().map(product -> new Button(product.getName() + ", " + product.getPrice() + "kr", () -> PurchaseItem(products.indexOf(product)), this::alwaysEnabled, MenuEnum.PURCHASE)).collect(Collectors.toList());
        Menu purchaseMenu = (new Builder()).header("What would you like to do?").addBtn(purchaseMenuBtns).backTo(MenuEnum.WARE).footer(() -> "Your funds: " + currentCustomer.getFunds()).build();

        menus.put(MenuEnum.MAIN, mainMenu);
        menus.put(MenuEnum.COSTUMER, customerMenu);
        menus.put(MenuEnum.LOGIN, loginMenu);
        menus.put(MenuEnum.WARE, wareMenu);
        menus.put(MenuEnum.SORT, sortMenu);
        menus.put(MenuEnum.PURCHASE, purchaseMenu);
        currentMenu = mainMenu;


        actions.put("back",this::back);
        actions.put("b",this::back);

        actions.put("left",this::left);
        actions.put("l",this::left);

        actions.put("right",this::right);
        actions.put("r",this::right);

        actions.put("ok",this::ok);
        actions.put("o",this::ok);

        actions.put("quit",this::quit);
        actions.put("q",this::quit);
    }

    void run() {
        System.out.println("Welcome to the WebShop!");
        while (true) {
            currentMenu.view();
            if (currentCustomer != null) {
                System.out.println("Current user: " + currentCustomer.getUsername());
            } else {
                System.out.println("Nobody logged in.");
            }

            String choice = scanner.nextLine().toLowerCase();
            if (actions.containsKey(choice))
                actions.get(choice).run();
            else
                System.out.println("That is not an applicable option.");
        }
    }
    public void left(){
            currentMenu.left();
    }
    public void right(){
            currentMenu.right();
    }

    public void ok(){
            currentMenu = menus.get(currentMenu.select());
            currentMenu.resetCursor();;
    }
    public void back(){
        currentMenu = menus.get(currentMenu.backTo);
    }

    public void quit(){
        System.out.println("The console powers down. You are free to leave.");
    }







    public boolean logoutEnabled() {
        return currentCustomer != null;
    }

    public boolean loginEnabled() {
        return currentCustomer == null;
    }

    public boolean usernamePasswordCompelte(){
        return (username == null || password == null);
    }

    public boolean foundUser(){
        boolean found = false;
        if(!usernamePasswordCompelte()) {
            for (Customer customer : customers) {
                if (username.equals(customer.getUsername()) && customer.CheckPassword(password)) {
                    foundCustomer = customer;
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean alwaysEnabled() {
        return true;
    }


    // Main Menu Logout
    public void printLogoutMessage() {
        System.out.println();
        System.out.println(currentCustomer.getUsername() + " logged out.");
        System.out.println();
        currentCustomer = null;
    }

    // Customer Menu See your order
    public void PrintCustomerOrders() {
        currentCustomer.PrintOrders();
    }

    // Customer Menu See your info
    public void PrintCustomerInfo() {
        currentCustomer.PrintInfo();
    }

    // Customer Menu Add fund
    public void GetFundAmount() {
        System.out.println("How many funds would you like to add?");
        String amountString = scanner.nextLine();
        try {
            int amount = Integer.parseInt(amountString);
            if (amount < 0) {
                System.out.println();
                System.out.println("Don't add negative amounts.");
                System.out.println();
            } else {
                currentCustomer.addFunds(amount);
                System.out.println();
                System.out.println(amount + " added to your profile.");
                System.out.println();
            }
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("Please write a number next time.");
            System.out.println();
        }
    }

    // Login Menu Password
    public void GetPassword() {
        System.out.println("A keyboard appears.");
        System.out.println("Please input your password.");
        password = scanner.nextLine();
        System.out.println();
    }

    // Login Menu Username
    public void InputUsename() {
        System.out.println("A keyboard appears.");
        System.out.println("Please input your username.");
        username = scanner.nextLine();
        System.out.println();
    }
    // Login Menu Login
    public  void ShowInvalidAuthurization(){
        System.out.println();
        System.out.println("Invalid credentials.");
        System.out.println();
    }

    public  void ShowIncompleteData(){
        System.out.println();
        System.out.println("Incomplete data.");
        System.out.println();
    }

    public  void ShowLoggedInUsername(){
        System.out.println();
        System.out.println(foundCustomer.getUsername() + " logged in.");
        System.out.println();
        currentCustomer = foundCustomer;
    }

//    // Login Menu Login
//    public void Activity11() {
//        if (username == null || password == null) {
//            System.out.println();
//            System.out.println("Incomplete data.");
//            System.out.println();
//        } else {
//            boolean found = false;
//            for (Customer customer : customers) {
//                if (username.equals(customer.getUsername()) && customer.CheckPassword(password)) {
//                    System.out.println();
//                    System.out.println(customer.getUsername() + " logged in.");
//                    System.out.println();
//                    currentCustomer = customer;
//                    found = true;
//                    break;
//                }
//            }
//            if (found == false) {
//                System.out.println();
//                System.out.println("Invalid credentials.");
//                System.out.println();
//            }
//        }
//    }

    // Login Menu Register
    public void RegisterInfo() {
        System.out.println("Please write your username.");
        String newUsername = scanner.nextLine();
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                System.out.println();
                System.out.println("Username already exists.");
                System.out.println();
                break;
            }
        }
        // Would have liked to be able to quit at any time in here.
        String choice = "";
        boolean next = false;
        String newPassword = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        int age = -1;
        String address = null;
        String phoneNumber = null;
        while (true) {
            System.out.println("Do you want a password? y/n");
            choice = scanner.nextLine();
            if (choice.equals("y")) {
                while (true) {
                    System.out.println("Please write your password.");
                    newPassword = scanner.nextLine();
                    if (newPassword.equals("")) {
                        System.out.println();
                        System.out.println("Please actually write something.");
                        System.out.println();
                        continue;
                    } else {
                        next = true;
                        break;
                    }
                }
            }
            if (choice.equals("n") || next) {
                next = false;
                break;
            }
            System.out.println();
            System.out.println("y or n, please.");
            System.out.println();
        }
        while (true) {
            System.out.println("Do you want a first name? y/n");
            choice = scanner.nextLine();
            if (choice.equals("y")) {
                while (true) {
                    System.out.println("Please write your first name.");
                    firstName = scanner.nextLine();
                    if (firstName.equals("")) {
                        System.out.println();
                        System.out.println("Please actually write something.");
                        System.out.println();
                        continue;
                    } else {
                        next = true;
                        break;
                    }
                }
            }
            if (choice.equals("n") || next) {
                next = false;
                break;
            }
            System.out.println();
            System.out.println("y or n, please.");
            System.out.println();
        }
        while (true) {
            System.out.println("Do you want a last name? y/n");
            choice = scanner.nextLine();
            if (choice.equals("y")) {
                while (true) {
                    System.out.println("Please write your last name.");
                    lastName = scanner.nextLine();
                    if (lastName.equals("")) {
                        System.out.println();
                        System.out.println("Please actually write something.");
                        System.out.println();
                        continue;
                    } else {
                        next = true;
                        break;
                    }
                }
            }
            if (choice.equals("n") || next) {
                next = false;
                break;
            }
            System.out.println();
            System.out.println("y or n, please.");
            System.out.println();
        }
        while (true) {
            System.out.println("Do you want an email? y/n");
            choice = scanner.nextLine();
            if (choice.equals("y")) {
                while (true) {
                    System.out.println("Please write your email.");
                    email = scanner.nextLine();
                    if (email.equals("")) {
                        System.out.println();
                        System.out.println("Please actually write something.");
                        System.out.println();
                        continue;
                    } else {
                        next = true;
                        break;
                    }
                }
            }
            if (choice.equals("n") || next) {
                next = false;
                break;
            }
            System.out.println();
            System.out.println("y or n, please.");
            System.out.println();
        }
        while (true) {
            System.out.println("Do you want an age? y/n");
            choice = scanner.nextLine();
            if (choice.equals("y")) {
                while (true) {
                    System.out.println("Please write your age.");
                    String ageString = scanner.nextLine();
                    try {
                        age = Integer.parseInt(ageString);
                    } catch (NumberFormatException e) {
                        System.out.println();
                        System.out.println("Please write a number.");
                        System.out.println();
                        continue;
                    }
                    next = true;
                    break;
                }
            }
            if (choice.equals("n") || next) {
                next = false;
                break;
            }
            System.out.println();
            System.out.println("y or n, please.");
            System.out.println();
        }
        while (true) {
            System.out.println("Do you want an address? y/n");
            choice = scanner.nextLine();
            if (choice.equals("y")) {
                while (true) {
                    System.out.println("Please write your address.");
                    address = scanner.nextLine();
                    if (address.equals("")) {
                        System.out.println();
                        System.out.println("Please actually write something.");
                        System.out.println();
                        continue;
                    } else {
                        next = true;
                        break;
                    }
                }
            }
            if (choice.equals("n") || next) {
                next = false;
                break;
            }
            System.out.println();
            System.out.println("y or n, please.");
            System.out.println();
        }
        while (true) {
            System.out.println("Do you want a phone number? y/n");
            choice = scanner.nextLine();
            if (choice.equals("y")) {
                while (true) {
                    System.out.println("Please write your phone number.");
                    phoneNumber = scanner.nextLine();
                    if (phoneNumber.equals("")) {
                        System.out.println();
                        System.out.println("Please actually write something.");
                        System.out.println();
                        continue;
                    } else {
                        next = true;
                        break;
                    }
                }
            }
            if (choice.equals("n") || next) {
                break;
            }
            System.out.println();
            System.out.println("y or n, please.");
            System.out.println();
        }

        Customer newCustomer = new Customer(newUsername, newPassword, firstName, lastName, email, age, address, phoneNumber);
        customers.add(newCustomer);
        currentCustomer = newCustomer;
        System.out.println();
        System.out.println(newCustomer.getUsername() + " successfully added and is now logged in.");
        System.out.println();

    }

    // Purchase Menu
    void PurchaseItem(int index) {
        Product product = products.get(index);
        if (product.InStock()) {
            if (currentCustomer.CanAfford(product.getPrice())) {
                currentCustomer.removeFunds(product.getPrice());
                product.decreaseStock();
                currentCustomer.getOrders().add(new Order(product.getName(), product.getPrice(), LocalDateTime.now()));
                System.out.println();
                System.out.println("Successfully bought " + product.getName());
                System.out.println();
            } else {
                System.out.println();
                System.out.println("You cannot afford.");
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("Not in stock.");
            System.out.println();
        }
    }


    // Ware Menu See all wares
    void ShowProductInfo() {
        System.out.println();
        for (Product product : products) {
            product.PrintInfo();
        }
        System.out.println();
    }

    // Ware Menu Purchase wares
    // null
    // void Activity151() {
    // }

    void ShowLoginError() {
        System.out.println();
        System.out.println("You must be logged in.");
        System.out.println();
    }

    // Ware Menu Sort wares

    // Ware Menu Logout
    void ShowUsernameLogout() {
        System.out.println();
        System.out.println(currentCustomer.getUsername() + " logged out.");
        System.out.println();
        currentCustomer = null;
    }

    // Ware Menu Login


    // Sort Menu Sort by name in
    void SortByNameIn() {
        bubbleSort("name", false);
        System.out.println();
        System.out.println("Wares sorted.");
        System.out.println();
    }

    // Sort Menu Sort by name as
    void SortByNameDe() {
        bubbleSort("name", true);
        System.out.println();
        System.out.println("Wares sorted.");
        System.out.println();
    }

    // Sort Menu Sort by price in
    void SortByPriceIn() {
        bubbleSort("price", false);
        System.out.println();
        System.out.println("Wares sorted.");
        System.out.println();
    }

    // Sort Menu Sort by price as
    void SortByPriceDe() {
        bubbleSort("price", true);
        System.out.println();
        System.out.println("Wares sorted.");
        System.out.println();
    }


    private void bubbleSort(String variable, boolean ascending) {
        if (variable.equals("name")) {
            int length = products.size();
            for (int i = 0; i < length - 1; i++) {
                boolean sorted = true;
                int length2 = length - i;
                for (int j = 0; j < length2 - 1; j++) {
                    if (ascending) {
                        if (products.get(j).getName().compareTo(products.get(j + 1).getName()) < 0) {
                            Product temp = products.get(j);
                            products.set(j, products.get(j + 1));
                            products.set(j + 1, temp);
                            sorted = false;
                        }
                    } else {
                        if (products.get(j).getName().compareTo(products.get(j + 1).getName()) > 0) {
                            Product temp = products.get(j);
                            products.set(j, products.get(j + 1));
                            products.set(j + 1, temp);
                            sorted = false;
                        }
                    }
                }
                if (sorted) {
                    break;
                }
            }
        } else if (variable.equals("price")) {
            int length = products.size();
            for (int i = 0; i < length - 1; i++) {
                boolean sorted = true;
                int length2 = length - i;
                for (int j = 0; j < length2 - 1; j++) {
                    if (ascending) {
                        if (products.get(j).getPrice() > products.get(j + 1).getPrice()) {
                            Product temp = products.get(j);
                            products.set(j, products.get(j + 1));
                            products.set(j + 1, temp);
                            sorted = false;
                        }
                    } else {
                        if (products.get(j).getPrice() < products.get(j + 1).getPrice()) {
                            Product temp = products.get(j);
                            products.set(j, products.get(j + 1));
                            products.set(j + 1, temp);
                            sorted = false;
                        }
                    }
                }
                if (sorted) {
                    break;
                }
            }
        }
    }

}
