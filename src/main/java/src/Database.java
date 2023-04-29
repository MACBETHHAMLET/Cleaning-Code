package src;

import java.util.ArrayList;

public class Database {
    // We just pretend this accesses a real database.
    private ArrayList<Product> productsInDatabase;
    private ArrayList<Customer> customersInDatabase;

    public Database() {
        productsInDatabase = new ArrayList<Product>();
        productsInDatabase.add(new Product("Mirror", 300, 2));
        productsInDatabase.add(new Product("Car", 2000000, 2));
        productsInDatabase.add(new Product("Candle", 50, 2));
        productsInDatabase.add(new Product("Computer", 100000, 2));
        productsInDatabase.add(new Product("Game", 599, 2));
        productsInDatabase.add(new Product("Painting", 399, 2));
        productsInDatabase.add(new Product("Chair", 500, 2));
        productsInDatabase.add(new Product("Table", 1000, 2));
        productsInDatabase.add(new Product("Bed", 20000, 2));

        customersInDatabase = new ArrayList<Customer>();
        customersInDatabase.add(new Customer("jimmy", "jimisthebest", "Jimmy", "Jamesson", "jj@mail.com", 22, "Big Street 5", "123456789"));
        customersInDatabase.add(new Customer("jake", "jake123", "Jake", null, null, 0, null, null));
    }

    public ArrayList<Product> GetProducts() {
        return productsInDatabase;
    }

    public ArrayList<Customer> GetCustomers() {
        return customersInDatabase;
    }
}
