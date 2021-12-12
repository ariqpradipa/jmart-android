package com.AriqJmartFA.model;

import java.util.ArrayList;
import java.util.Date;

public class Payment extends Invoice {

    public ArrayList<Record> history = new ArrayList<Record>();
    public int productCount;
    public Shipment shipment;

    public Payment(int buyerId, int productId, int productCount, Shipment shipment) {

        super(buyerId, productId);
        this.productCount = productCount;
        this.shipment = shipment;

    }

    public static class Record {

        public final Date date = new Date();
        public String message;
        public Status status;

    }
}
