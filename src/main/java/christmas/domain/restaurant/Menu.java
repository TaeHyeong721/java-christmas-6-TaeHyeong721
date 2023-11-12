package christmas.domain.restaurant;

import christmas.util.ErrorMessage;
import java.util.Arrays;

public enum Menu {
    MUSHROOM_SOUP(Category.APPETIZER, 6_000, "양송이수프"),
    TAPAS(Category.APPETIZER, 5_500, "타파스"),
    CAESAR_SALAD(Category.APPETIZER, 8_000, "시저샐러드"),
    T_BONE_STEAK(Category.MAIN_COURSE, 55_000, "티본스테이크"),
    BBQ_RIBS(Category.MAIN_COURSE, 54_000, "바비큐립"),
    SEAFOOD_PASTA(Category.MAIN_COURSE, 35_000, "해산물파스타"),
    CHRISTMAS_PASTA(Category.MAIN_COURSE, 25_000, "크리스마스파스타"),
    CHOCOLATE_CAKE(Category.DESSERT, 15_000, "초코케이크"),
    ICE_CREAM(Category.DESSERT, 5_000, "아이스크림"),
    ZERO_COLA(Category.BEVERAGE, 3_000, "제로콜라"),
    RED_WINE(Category.BEVERAGE, 60_000, "레드와인"),
    CHAMPAGNE(Category.BEVERAGE, 25_000, "샴페인");

    private final Category category;
    private final int price;
    private final String name;

    Menu(Category category, int price, String name) {
        this.category = category;
        this.price = price;
        this.name = name;
    }

    public static Menu from(String menuName) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getName().equals(menuName))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(ErrorMessage.NOT_FOUND_MENU_BY_NAME.getMessage())
                );
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
