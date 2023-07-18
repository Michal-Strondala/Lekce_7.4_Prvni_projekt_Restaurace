package com.engeto.first.project;

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
                    Settings.DELIMITER + "Čas přípravy: " +
                    dish.getPreparationTimeInMinutes() + " min," +
                    Settings.DELIMITER + "Fotografie: " +
                    dish.getImage() + "," +
                    Settings.DELIMITER + "Kategorie: " +
                    dish.getCategory().getDescription());
            i++;
        }
        return returnString;
    }
}
