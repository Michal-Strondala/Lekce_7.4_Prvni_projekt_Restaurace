package com.engeto.first.project;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Dish /*jídla*/ extends Cookbook {
    // region Atributy
    private int dishId; // jedinečné id jídla (něco jako čárový kód)
    private String title; // název jídla
    private BigDecimal price; // cena
    private int preparationTimeInMinutes; // přibližná doba přípravy v min
    private String image; // název souboru fotografie ve formátu: bolonske-spagety-01
    private List<String> images = new ArrayList<>(); // Seznam více fotografií
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

    // Konstruktor pro vložení seznamu více obrázků
    public Dish(int dishId, String title, BigDecimal price, int preparationTimeInMinutes, List<String> images, Category category) throws RestaurantException {
        this.dishId = dishId;
        this.title = title;
        this.price = price;
        this.preparationTimeInMinutes = preparationTimeInMinutes;
        this.images = images;
            if (images.isEmpty()) {
                throw new RestaurantException("Je nutné vložit fotografii.");
            }
        this.category = category;
    }

    // Konstruktor, kde je obrázek prázdný
    public Dish(int dishId, String title, BigDecimal price, int preparationTimeInMinutes, Category category) throws RestaurantException {
        this(dishId, title, price, preparationTimeInMinutes, "blank", category);
    }

    // Konstruktor pro pokrm načtený ze souboru.
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


    // region Metoda k odstranění fotografie z pokrmu, ale tak, aby tam vždy zůstala alespoň jedna.
    public void removeImage(String image) throws RestaurantException {
        try {
            List<String> images = getImages();
            if (images.size() > 1) {
                images.remove(image);
                System.out.println("Obrázek " + image + " byl odstraněn.");
                this.setImages(images); // Aktualizujeme seznam obrázků
            } else {
                throw new RestaurantException("Musí zůstat alespoň jedna fotografie.");
            }
        } catch (Exception e) {
            throw new RestaurantException("Chyba při odstraňování obrázku: " + e.getMessage());
        }
    }
    // endregion

    // endregion

    // region Přístupové metody
    public String getTitle() {
        return this.title;
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
        if (this.preparationTimeInMinutes == 0) {
            return "neznámé";
        }
        return String.valueOf(this.preparationTimeInMinutes);
    }

    public void setPreparationTimeInMinutes(int preparationTimeInMinutes) {
        this.preparationTimeInMinutes = preparationTimeInMinutes;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getDishId() {
        return this.dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void addImage(String name) {
        this.images.add(name);

        //kdyz hlavni image neni, priradim ho
        if(this.image == null) this.image = name;
    }

    public void setMainImage(String name) {
        this.image = name;

        if (!this.images.contains(name)) this.images.add(name);
    }
    // endregion
}
