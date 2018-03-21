package com.example.murti.coincap;

/**
 * Created by murti on 19.03.2018.
 */

public class Food {
    private int rank;
    private String name;
    private double price;

    public Food(String name, double price, int rank) {
        this.name = name;
        this.price = price;
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int id) {
        this.rank = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}