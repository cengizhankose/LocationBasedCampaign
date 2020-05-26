package com.example.locationbasedcampaign;

import com.google.android.gms.maps.model.LatLng;

public class Store {
    private String storeName;
    private LatLng storeCoordinates;
    private String  storeCampaign;

    public Store(String ad , LatLng koordinat , String kampanya)
    {
        this.storeName = ad;
        this.storeCoordinates = koordinat;
        this.storeCampaign = kampanya;
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