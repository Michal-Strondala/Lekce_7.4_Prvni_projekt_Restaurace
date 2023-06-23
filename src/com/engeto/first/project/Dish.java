package com.engeto.first.project;

import java.time.LocalDateTime;

public class Dish /*jídla*/ extends Cookbook {
    // Atributy

    private int dishId; // jedinecne id jidla neco jako carovy kod
    private String title; // název jídla
    private double price; // cena
    private int preparationTimeInMinutes; // přibližná doba přípravy v min
    private String image; // název souboru ve formátu: bolonske-spagety-01
    private Category category;


    // Konstruktory

    public Dish(int dishId, String title, double price, int preparationTimeInMinutes, String image, Category category) throws RestaurantException {
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
    public Dish(int dishId, String title, double price, int preparationTimeInMinutes, Category category) throws RestaurantException {
        this(dishId, title, price, preparationTimeInMinutes, "blank", category);
    }

    // Metody

    // Příklad, jak má vypadat výpis objednávek:
//    ** Objednávky pro stůl č.  4 **
//            ****
//            1. Kofola velká 4x (130 Kč):    15:25-15:29 číšník č. 3
//            2. Pizza Grande (130 Kč):   15:29-16:10 číšník č. 4
//            3. Nanuk Míša (30 Kč):  17:12-17:18 číšník č. 2
//            ******
    @Override
    public String toString() {
            return title;
    }




    // Přístupové metody

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        this.price = price;
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPreparationTimeInMinutes() {
        return preparationTimeInMinutes;
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
}
