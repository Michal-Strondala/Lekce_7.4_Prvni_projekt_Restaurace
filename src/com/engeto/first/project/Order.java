package com.engeto.first.project;


import java.time.LocalTime;
import java.util.Objects;

public class Order /*implements Comparable<Order>*/{
    // Atributy
    private Waiter waiter; // číslo číšníka
    private LocalTime orderedTime; // čas zadání objednávky
    private LocalTime fulfilmentTime; // čas vyřízení objednávky
    private Dish dishFromCurrentMenu; // objednané jídlo/pití z menu
    private String note; // poznámka zákazníka
    //private static int nextId = 1;
    //private int orderCount = nextId++;
    private int orderedAmount; // počet kusů v objednávce

    // Konstruktory

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
        this.orderedAmount = orderedAmount;
        this.fulfilmentTime = null;
        if (orderedAmount < 1)
            throw new RestaurantException("Minimální počet je 1");
        this.dishFromCurrentMenu = dishFromCurrentMenu;
        this.note = note;
    }


    //Přístupové metody

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

    public double getTotalPrice() {
        return dishFromCurrentMenu.getPrice() * this.getOrderedAmount();
    }

    public boolean isFinished() {
        if (this.getFulfilmentTime() != null)
            return true;

        return false;
    }

//    public boolean isTheSameWaiter() {
//        if (getWaiterId() == getWaiterId())
//            return true;
//
//        return false;
//    }



//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Order order = (Order) o;
//        return waiterId == order.waiterId;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(waiterId);
//    }

//    @Override
//    public int compareTo(Order second) {
//        return this.waiter.compareTo(second.waiter);
//    }



}
