package com.infy.model;

import java.util.ArrayList;

public class Nugget {

    private String brand;
    private ArrayList<ComboCost> cost;

    public String getBrand() {
        return brand;
    }

    public ArrayList<ComboCost> getCost() {
        return cost;
    }

    public void setCost(ArrayList<ComboCost> cost) {
        this.cost = cost;
    }

    public void setBrand(String brand) {
        this.brand = brand;

    }

    public Nugget(){

    }
    public Nugget(String brand) {
        this.brand = brand;
        this.cost = new ArrayList<>();
    }
}
