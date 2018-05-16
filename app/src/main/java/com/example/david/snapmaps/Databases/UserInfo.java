package com.example.david.snapmaps.Databases;

import java.util.ArrayList;

public class UserInfo {

    public static int userId;
    public static String email, password, firstName, lastName;

    public static ArrayList<Integer> locationIds = new ArrayList<Integer>();
    public static ArrayList<String> locationNames = new ArrayList<String>();
    public static ArrayList<String> locationAddresses = new ArrayList<String>();
    public static ArrayList<Double> latitudes = new ArrayList<Double>();
    public static ArrayList<Double> longitudes = new ArrayList<Double>();

    public static ArrayList<String> comments = new ArrayList<String>();

    public UserInfo() {

    }
}
