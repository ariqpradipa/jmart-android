package com.AriqJmartFA.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Store {

    public String address;
    public String balance;
    public String name;
    public String phoneNumber;

    public Store(String name, String address, String phoneNumber, String i) {

        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.balance = i;
    }
}
