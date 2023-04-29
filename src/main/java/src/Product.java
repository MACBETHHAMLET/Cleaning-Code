package src;

public class Product {
    private String name;
    private int price;
    private int nrInStock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNrInStock() {
        return nrInStock;
    }

    public void decreaseStock() {
        nrInStock--;
    }

    public Product(String name, int price, int nrInStock) {
        this.name = name;
        this.price = price;
        this.nrInStock = nrInStock;
    }

    public boolean InStock() {
        return nrInStock > 0;
    }

    public void PrintInfo() {
        System.out.println(name + ": " + price + "kr, " + nrInStock + " in stock.");
    }
}
