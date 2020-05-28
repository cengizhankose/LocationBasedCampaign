package com.example.locationbasedcampaign;

import com.google.android.gms.maps.model.LatLng;

public class Store {
    private String storeName;
    private LatLng storeCoordinates;
    private String  storeCampaign;
    private double storeDistanceWithUser;

    public Store(String ad , LatLng koordinat , String kampanya)
    {
        this.storeName = ad;
        this.storeCoordinates = koordinat;
        this.storeCampaign = kampanya;
    }

    public double getStoreDistanceWithUser() {
        return storeDistanceWithUser;
    }

    public void setStoreDistanceWithUser(double storeDistanceWithUser) {
        this.storeDistanceWithUser = storeDistanceWithUser;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public LatLng getStoreCoordinates() {
        return storeCoordinates;
    }

    public void setStoreCoordinates(LatLng storeCoordinates) {
        this.storeCoordinates = storeCoordinates;
    }

    public String getStoreCampaign() {
        return storeCampaign;
    }

    public void setStoreCampaign(String storeCampaign) {
        this.storeCampaign = storeCampaign;
    }

}