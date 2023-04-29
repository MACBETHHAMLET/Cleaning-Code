package src.menus;


import java.util.function.BooleanSupplier;

public class Button {
    public String name;
    public Runnable onClick;
    public BooleanSupplier enabled;
    public MenuEnum to;

    public Button(String name, Runnable onClick, BooleanSupplier enabled, MenuEnum to) {
        this.name = name;
        this.onClick = onClick;
        this.enabled = enabled;
        this.to = to;
    }
}
