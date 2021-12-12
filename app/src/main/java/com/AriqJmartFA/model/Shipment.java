package com.AriqJmartFA.model;

import java.text.SimpleDateFormat;

public class Shipment {

    public static final SimpleDateFormat ESTIMATION_FORMAT = new SimpleDateFormat("E MMMM dd yyyy");
    public static Plan INSTANT  = new Plan((byte)(0b00000001));
    public static Plan SAME_DAY = new Plan((byte)(0b00000010));
    public static Plan NEXT_DAY = new Plan((byte)(0b00000100));
    public static Plan REGULER  = new Plan((byte)(0b00001000));
    public static Plan KARGO    = new Plan((byte)(0b00010000));
    public String address;
    public int cost;
    public byte plan;
    public String receipt;

    public Shipment(String address, int cost, byte plan, String receipt) {

        this.address = address;
        this.cost = cost;
        this.plan = plan;
        this.receipt = receipt;

    }

    public static class Plan {

        byte bit;

        private Plan(byte bit) {

        }
    }
}
