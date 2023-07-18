package com.engeto.first.project;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public class Order {
    // region Atributy
    private Waiter waiter; // číslo číšníka
    private LocalTime orderedTime; // čas zadání objednávky
    private LocalTime fulfilmentTime; // čas vyřízení objednávky
    private Dish dishFromCurrentMenu; // objednané jídlo/pití z menu
    private String note; // poznámka zákazníka
    private int orderedAmount; // počet kusů v objednávce
    // endregion

    // region Konstruktory

    public Order(int tableNumber, Waiter waiter, LocalTime orderedTime, LocalTime fulfilmentTime, Dish dishFromCurrentMenu, int orderedAmount, String note) throws RestaurantException {
            if (tableNumber <= 0 || tableNumber > 30) {
                throw new RestaurantException("Takový stůl neexistuje. Zadej číslo mezi 1 až 30.");
            }
        this.waiter = waiter;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
        this.orderedAmount = orderedAmount;
            if (orderedAmount < 1)
                throw new RestaurantException("Minimální počet je 1");
        this.dishFromCurrentMenu = dishFromCurrentMenu;
        this.note = note;
    }

    public Order(int tableNumber, Waiter waiter, LocalTime orderedTime, Dish dishFromCurrentMenu, int orderedAmount, String note) throws RestaurantException {
        if (tableNumber <= 0 || tableNumber > 30) {
            throw new RestaurantException("Takový stůl neexistuje. Zadej číslo mezi 1 až 30.");
        }
        this.waiter = waiter;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = null;
        this.orderedAmount = orderedAmount;
            if (orderedAmount < 1)
                throw new RestaurantException("Minimální počet je 1");
        this.dishFromCurrentMenu = dishFromCurrentMenu;
        this.note = note;
    }
    // endregion


    // region Přístupové metody

    public int getWaiterId() { return waiter.getId(); }

    public LocalTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public Dish getDishFromCurrentMenu() {
        return dishFromCurrentMenu;
    }

    public void setDishFromCurrentMenu(Dish dishFromCurrentMenu) {
        this.dishFromCurrentMenu = dishFromCurrentMenu;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getOrderedAmount() {
        return orderedAmount;
    }

    public void setOrderedAmount(int orderedAmount) {
        this.orderedAmount = orderedAmount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = dishFromCurrentMenu.getPrice().multiply(BigDecimal.valueOf(this.getOrderedAmount()));
        return totalPrice.setScale(0, RoundingMode.HALF_UP);
    }
    // endregion

    // region Metoda k oveření zda je objednávka již dokončená
    public boolean isFinished() {
        if (this.getFulfilmentTime() != null) return true;
        return false;
    }

    public void close() {
        LocalTime timeFinished = this.getOrderedTime().plusMinutes(60);
        this.setFulfilmentTime(timeFinished);
    }
    // endregion
}
