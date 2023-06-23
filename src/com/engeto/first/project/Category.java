package com.engeto.first.project;

public enum Category {
    FOOD("jídlo"), DRINK("pití"), SIDE_DISH("příloha");

    private String description;

    Category(String description) {
        this.description = description;
    }
}
