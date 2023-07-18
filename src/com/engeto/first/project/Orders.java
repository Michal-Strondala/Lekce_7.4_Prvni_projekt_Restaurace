package com.engeto.first.project;

import java.util.ArrayList;

public class Orders  {

    // region Atributy
    private Table table;
    private ArrayList<Order> orders = new ArrayList<>();
    // endregion


    // region Konstruktory
    public Orders(Table table)
    {
        this.table = table;
    }

    public Orders() {}
    // endregion

    // region Metody

    public void setTable(Table table)
    {
        this.table = table;
    }

    public int getTableNumber() {
        return table.getNumber();
    }

    public void addOrder(Order order)
    {
        this.orders.add(order);
    }

    @Override
    public String toString() {

        String returnString = "";
        int i = 1;
        // Vypsání objednávek pro každý stůl

                for (Order order : this.orders) {
                        returnString += ("\n" +
                                i +
                                ". " +
                                order.getDishFromCurrentMenu() +
                                " " +
                                this.getTotalPriceAsString(order) +
                                Settings.DELIMITER +
                                order.getOrderedTime() +
                                this.orderClosed(order) +
                                Settings.DELIMITER +
                                " číšník č. " + order.getWaiterId());
                        i++;
                }


        return returnString;
    }

    // region Metoda k získání celkové ceny objednávky jako String
    private String getTotalPriceAsString(Order order) {
        if (order.getOrderedAmount() > 1)
            return order.getOrderedAmount() + "x (" + order.getTotalPrice()+" Kč):";
        else return " (" + order.getTotalPrice()+" Kč):";
    }
    // endregion

    // region Metoda k odlišení nedokončených objednávek při výpisu objednávek
    private String orderClosed(Order order) {
        if (order.getFulfilmentTime() == null)
            return "-objednávka nedokončena";
        else return "-" + order.getFulfilmentTime();
    }
    // endregion

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void clear()
    {
        this.orders.clear();
    }
    // endregion
}
