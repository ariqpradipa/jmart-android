package com.AriqJmartFA.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends Serializable {

    public static final String REGEX_EMAIL = "(?:[A-Za-z0-9&_*~]+(?:\\.[A-Za-z0-9&_*~]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[A-Za-z0-9-]*[A-Za-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    public static final String REGEX_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?!.*?[\\s]).{8,}$";
    public double balance;
    public String name;
    public String email;
    public String password;
    public Object store;

    public Account(String name, String email, String password, double balance) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        
    }

    public boolean validate() {

        Pattern emailPattern = Pattern.compile(REGEX_EMAIL);
        Matcher emailMatcher = emailPattern.matcher(this.email);

        Pattern passwordPattern = Pattern.compile(REGEX_PASSWORD);
        Matcher passwordMatcher = passwordPattern.matcher(this.password);

        return emailMatcher.matches() && passwordMatcher.matches();
        
    }
}
