package com.engeto.first.project;

import java.util.Comparator;

public class WaiterComparator implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        return Integer.compare(order1.getWaiterId(), order2.getWaiterId());
    }
}
