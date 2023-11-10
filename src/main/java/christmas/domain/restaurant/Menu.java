package christmas.domain.restaurant;

public record Menu(MenuCategory category, Food food) {

    public int getPrice() {
        return food.getPrice();
    }
}
