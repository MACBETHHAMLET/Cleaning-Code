package src;
import src.old.WebShopOld;

public class Main {
    public static void main(String[] args) {
        // Singltone Pattern Design
        WebShop webShop = WebShop.getInstance();
        webShop.run();
    }
}