package com.engeto.first.project;

public enum Category {
    FOOD("jídlo"),
    DRINK("pití"),
    SIDE_DISH("příloha"),
    UNCATEGORIZED("nezařazeno");

    private String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
