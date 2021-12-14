package com.AriqJmartFA.model;

import java.util.ArrayList;
import java.util.Date;

public class Payment extends Invoice {

    public int id;
    public Record latestHistory;
    public String date;
    public int productCount;
    public Shipment shipment;

    public Payment(int id, String date, int buyerId, int productId, int productCount, Shipment shipment, Record latestHistory) {

        super(buyerId, productId);
        this.id = id;
        this.date = date.substring(0, 10);
        this.productCount = productCount;
        this.shipment = shipment;
        this.latestHistory = latestHistory;

    }

    public static class Record {

        public String date;
        public String message;
        public String status;

        public Record(String date, String message, String status) {

            this.date = date.substring(0, 10);
            this.message = message;
            this.status = status;
        }

    }
}
