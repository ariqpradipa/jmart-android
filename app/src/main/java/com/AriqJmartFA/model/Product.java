package com.AriqJmartFA.model;

public class Product extends Serializable {

    public int accountId;
    public String category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public int shipmentPlan;
    public int weight;

    public Product(int accountId, String category, boolean conditionUsed, double discount, String name, double price, int shipmentPlan, int weigth) {

        this.accountId = accountId;
        this.category = category;
        this.conditionUsed = conditionUsed;
        this.name = name;
        this.discount = discount;
        this.price = price;
        this.shipmentPlan = shipmentPlan;
        this.weight = weigth;
    }

    public String toString() {

        return name;

    }
}
