package com.example.locationbasedcampaign;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariables extends Application {
    public static List<User>userList = new ArrayList<User>();
    public static List<Store>storeList = new ArrayList<Store>();
    public static double chosenDistance;
    public static String category;
}
