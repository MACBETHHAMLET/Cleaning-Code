package src;

import java.util.ArrayList;

public class Customer {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String address;
    private String phoneNumber;
    private int funds;
    private ArrayList<Order> orders;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getFunds() {
        return funds;
    }

    public void addFunds(int amount) {
        funds += amount;
    }

    public void removeFunds(int amount) {
        funds -= amount;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Customer(String username, String password, String firstName, String lastName, String email, int age, String address, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        orders = new ArrayList<>();
        funds = 0;
    }

    public boolean CanAfford(int price) {
        return funds >= price;
    }

    public boolean CheckPassword(String password) {
        if (password == null) {
            return true;
        }
        return password.equals(this.password);
    }

    public void PrintInfo() {
        System.out.println();
        System.out.print("Username: " + username + "");
        if (password != null) {
            System.out.print(", Password: " + password);
        }
        if (firstName != null) {
            System.out.print(", First Name: " + firstName);
        }
        if (lastName != null) {
            System.out.print(", Last Name: " + lastName);
        }
        if (email != null) {
            System.out.print(", Email: " + email);
        }
        if (age != -1) {
            System.out.print(", Age: " + age);
        }
        if (address != null) {
            System.out.print(", Address: " + address);
        }
        if (phoneNumber != null) {
            System.out.print(", Phone Number: " + phoneNumber);
        }
        System.out.println(", Funds: " + funds);
        System.out.println();
    }

    public void PrintOrders() {
        System.out.println();
        for (Order order : orders) {
            order.PrintInfo();
        }
        System.out.println();
    }
}
