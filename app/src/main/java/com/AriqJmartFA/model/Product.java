package com.AriqJmartFA.model;

public class Product extends Serializable {

    public int id;
    public int accountId;
    public String category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public int shipmentPlan;
    public int weight;
    public String storeName;

    public Product(int id, int accountId, String category, boolean conditionUsed, double discount, String name, double price, int shipmentPlan, int weight, String storeName) {

        this.id = id;
        this.accountId = accountId;
        this.category = category;
        this.conditionUsed = conditionUsed;
        this.name = name;
        this.discount = discount;
        this.price = price;
        this.shipmentPlan = shipmentPlan;
        this.weight = weight;
        this.storeName = storeName;
    }

    public String toString() {

        return name;

    }
}
