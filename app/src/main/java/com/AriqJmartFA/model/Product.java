package com.AriqJmartFA.model;

public class Product extends Serializable {

    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlan;
    public int weight;

    public String toString() {

        return name;

    }
}
