package src;

import java.time.LocalDateTime;

public class Order {
    private String name;
    private int boughtFor;
    private LocalDateTime purchaseTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBoughtFor() {
        return boughtFor;
    }

    public void setBoughtFor(int boughtFor) {
        this.boughtFor = boughtFor;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Order(String name, int boughtFor, LocalDateTime purchaseTime) {
        this.name = name;
        this.boughtFor = boughtFor;
        this.purchaseTime = purchaseTime;
    }

    public void PrintInfo() {
        System.out.println(name + ", bought for " + boughtFor + "kr, time: " + purchaseTime + ".");
    }
}
