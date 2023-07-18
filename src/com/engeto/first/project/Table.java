package com.engeto.first.project;

public class Table {

    // region Atributy
    int number;
    // endregion

    // region Konstruktory

    public Table(int number) {
        this.number = number;
    }

    public Table() {
    }
    // endregion

    // region Přístupové metody

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    // endregion
}
