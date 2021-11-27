package com.AriqJmartFA.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Store extends Serializable {

    public static final String REGEX_NAME = "(?=^.{4,20}$)^[A-Z]([A-Za-z][\\s]?)+$";
    public static final String REGEX_PHONE = "\\(?(?:\\+62|62|0)[ .-]?\\d{8,11}";
    public String address;
    public double balance;
    public String name;
    public String phoneNumber;

    public Store(String name, String address, String phoneNumber, double balance) {

        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.balance = balance;

    }

    public String toString() {

        String nama = "Name: " + this.name;
        String jalan = "\nAddress: " + this.address;
        String notelp = "\nPhoneNumber: " + this.phoneNumber;
        String bal = "\nBalance: " + this.balance;

        return (nama + jalan + notelp + bal);

        // return "name: PT Madju Merdeka\naddress: Jl. Kukusan]nphoneNumber: 628777xxxx";

    }

    public boolean validate() {

        Pattern patternPhone = Pattern.compile(REGEX_PHONE);
        Matcher matcherPhone = patternPhone.matcher(this.phoneNumber);

        Pattern patternName = Pattern.compile(REGEX_NAME);
        Matcher matcherName = patternName.matcher(this.name);

        return matcherPhone.matches() && matcherName.matches();

    }
}
