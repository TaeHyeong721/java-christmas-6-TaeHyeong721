package christmas.domain.restaurant;

public enum Menu {
    MUSHROOM_SOUP(Category.APPETIZER, 6_000),
    TAPAS(Category.APPETIZER, 5_500),
    CAESAR_SALAD(Category.APPETIZER, 8_000),
    T_BONE_STEAK(Category.MAIN_COURSE, 55_000),
    BBQ_RIBS(Category.MAIN_COURSE, 54_000),
    SEAFOOD_PASTA(Category.MAIN_COURSE, 35_000),
    CHRISTMAS_PASTA(Category.MAIN_COURSE, 25_000),
    CHOCOLATE_CAKE(Category.DESSERT, 15_000),
    ICE_CREAM(Category.DESSERT, 5_000),
    ZERO_COLA(Category.BEVERAGE, 3_000),
    RED_WINE(Category.BEVERAGE, 60_000),
    CHAMPAGNE(Category.BEVERAGE, 25_000);

    private final Category category;
    private final int price;

    Menu(Category category, int price) {
        this.category = category;
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }
    public int getPrice() {
        return price;
    }
}
