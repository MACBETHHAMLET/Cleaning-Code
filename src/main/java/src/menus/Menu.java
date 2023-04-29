package src.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.lang.Math.max;

public class Menu {
    public MenuEnum backTo;
    public List<Button> buttons = new ArrayList<>();
    public String header;
    public Supplier<String> footer;

    private int cursor;

    public void right() {
        List<Button> activeBtns = buttons.stream().filter(button -> button.enabled.getAsBoolean()).toList();
        int activeBtnsCount = activeBtns.size();
        if (cursor < activeBtnsCount - 1) cursor++;
    }

    public void left() {
        if (cursor > 0) {
            cursor--;
        }
    }

    public MenuEnum select() {
        List<Button> activeBtns = buttons.stream().filter(button -> button.enabled.getAsBoolean()).toList();
        if (activeBtns.get(cursor).onClick != null) activeBtns.get(cursor).onClick.run();
        return activeBtns.get(cursor).to;
    }


    public void view() {
        List<Button> activeBtns = buttons.stream().filter(button -> button.enabled.getAsBoolean()).toList();
        int activeBtnsCount = activeBtns.size();
        System.out.println("------------------------------");
        if (header != null) System.out.println(header);
        for (int i = 1; i <= activeBtnsCount; i++) {
            System.out.println(i + ": " + activeBtns.get(i - 1).name);
        }
        if (footer != null) System.out.println(footer.get());
        for (int i = 1; i <= activeBtnsCount; i++) {
            System.out.print(i + "\t");
        }
        System.out.print("\n"+"\t".repeat(cursor)+"|\n");
        System.out.println("Your buttons are Left, Right, OK, Back and Quit.");
    }

    public void resetCursor() {
        cursor = 0;
    }
}
