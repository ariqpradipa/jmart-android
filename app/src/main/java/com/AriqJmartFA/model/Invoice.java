package com.AriqJmartFA.model;

import java.util.Date;

public class Invoice extends Serializable {

    public int buyerId;
    public int complaintId;
    public Date date;
    public int productId;
    public Rating rating;

    protected Invoice(int buyerId, int productId) {

        this.buyerId = buyerId;
        this.productId = productId;
        this.complaintId = -1;
        this.date = new Date();
        this.rating = Rating.NONE;

    }

    public static enum Status{

        CANCELLED, COMPLAINT, FAILED, FINISHED, ON_DELIVERY,
        ON_PROGRESS, WAITING_CONFIRMATION

    }

    public static enum Rating {

        BAD, GOOD, NEUTRAL, NONE

    }

}
