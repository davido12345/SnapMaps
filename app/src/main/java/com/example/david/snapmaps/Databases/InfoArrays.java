package com.example.david.snapmaps.Databases;


import java.util.ArrayList;

public class InfoArrays {

    //DON'T USE...
    //this is only for logging in successfully in the main Activity
    //after that ONLY use UserInfo

    public static ArrayList<String> emails = new ArrayList<String>();
    public static ArrayList<String> passwords = new ArrayList<String>();
    public static ArrayList<Integer> userIds = new ArrayList<Integer>();
    public static ArrayList<String> firstNames = new ArrayList<String>();
    public static ArrayList<String> lastNames = new ArrayList<String>();

    public static ArrayList<Integer> locationIds = new ArrayList<Integer>();
    public static ArrayList<String> locationNames = new ArrayList<String>();
    public static ArrayList<String> locationAddresses = new ArrayList<String>();
    public static ArrayList<Double> latitudes = new ArrayList<Double>();
    public static ArrayList<Double> longitudes = new ArrayList<Double>();

    public static ArrayList<Integer> userLocation_UserIds = new ArrayList<Integer>();
    public static ArrayList<Integer> userLocation_locationIds = new ArrayList<Integer>();
    public static ArrayList<String> comments = new ArrayList<String>();

    public InfoArrays() {

    }
}
