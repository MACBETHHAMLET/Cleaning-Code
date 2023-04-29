package src.menus;

import java.util.List;
import java.util.function.Supplier;

public class Builder {
    private final Menu menu = new Menu();

    public Builder addBtn(List<Button> bottons) {
        menu.buttons.addAll(bottons);
        return this;
    }

    public Builder addBtn(Button botton) {
        menu.buttons.add(botton);
        return this;
    }

    public Builder backTo(MenuEnum backTo) {
        menu.backTo = backTo;
        return this;

    }

    public Builder header(String heater) {
        menu.header = heater;
        return this;
    }

    public Builder footer(Supplier<String> footer) {
        menu.footer = footer;
        return this;
    }

    public Menu build() {
        return menu;
    }


}

