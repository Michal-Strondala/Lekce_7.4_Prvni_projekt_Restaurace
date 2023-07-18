package com.engeto.first.project;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Dish /*jídla*/ extends Cookbook {
    // region Atributy
    private int dishId; // jedinecne id jidla neco jako carovy kod
    private String title; // název jídla
    private BigDecimal price; // cena
    private int preparationTimeInMinutes; // přibližná doba přípravy v min
    private String image; // název souboru ve formátu: bolonske-spagety-01
    private Category category;
    // endregion


    // region Konstruktory
    public Dish(int dishId, String title, BigDecimal price, int preparationTimeInMinutes, String image, Category category) throws RestaurantException {
        this.dishId = dishId;
        this.title = title;
        this.price = price;
        this.preparationTimeInMinutes = preparationTimeInMinutes;
        this.image = image;
            if (image.isEmpty()) {
                throw new RestaurantException("Je nutné vložit fotografii.");
            }
        this.category = category;
    }
    public Dish(int dishId, String title, BigDecimal price, int preparationTimeInMinutes, Category category) throws RestaurantException {
        this(dishId, title, price, preparationTimeInMinutes, "blank", category);
    }

    public Dish(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
        this.image = "blank";
        this.category = Category.UNCATEGORIZED;
    }
    // endregion

    // region Metody
    @Override
    public String toString() {
            return title;
    }
    // endregion

    // region Přístupové metody
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPreparationTimeInMinutes() {
        if (preparationTimeInMinutes == 0) {
            return "neznámé";
        }
        return String.valueOf(preparationTimeInMinutes);
    }

    public void setPreparationTimeInMinutes(int preparationTimeInMinutes) {
        this.preparationTimeInMinutes = preparationTimeInMinutes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }
    // endregion
}
