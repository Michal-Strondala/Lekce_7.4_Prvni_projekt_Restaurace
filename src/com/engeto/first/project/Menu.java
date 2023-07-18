package com.engeto.first.project;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Menu extends Cookbook{

    // region Metoda k ověření zda je vybraný pokrm z aktuálního menu
    public boolean isDishFromCurrentMenu(Dish currentDish) throws RestaurantException {

        for (Dish dish : this) {
            if (dish.getDishId() == currentDish.getDishId()) {
                return true;
            }
        }
        throw new RestaurantException("Toto jídlo [" + currentDish.getTitle() + "] není součástí aktuálního menu.");
    }
    // endregion

    // region Metoda k odstranění pokrmu z aktuálního menu
    public void removeDishFromCurrentMenu (Dish currentDish) {
        for (Dish dish : this) {
            if (dish.getTitle().equals(currentDish.getTitle())) {
                this.remove(currentDish);
            }
        }
    }
    // endregion

    @Override
    public String toString() {
        String returnString = "";
        int i = 1;
        // Vypsání každého pokrmu v zásobníku jídel
        for (Dish dish : this) {
            returnString += ("\n" +
                    i +
                    ". " +
                    dish.getTitle() +
                    " = Cena: " +
                    dish.getPrice() + " Kč," +
                    Settings.delimiter() + "Čas přípravy: " +
                    dish.getPreparationTimeInMinutes() + " min," +
                    Settings.delimiter() + "Fotografie: " +
                    dish.getImage() + "," +
                    Settings.delimiter() + "Kategorie: " +
                    dish.getCategory().getDescription());
            i++;
        }
        return returnString;
    }




}
