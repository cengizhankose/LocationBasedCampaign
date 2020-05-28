package com.example.locationbasedcampaign.ui;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.locationbasedcampaign.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.locationbasedcampaign.GlobalVariables.chosenCategorie;
import static com.example.locationbasedcampaign.GlobalVariables.storeList;
import static com.example.locationbasedcampaign.GlobalVariables.chosenDistance;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity" ;
    Button allChoicesBtn;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        allChoicesBtn=(Button)findViewById(R.id.map_allchoices);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i=0; i<storeList.size();i++){
            if(storeList.get(i).getStoreDistanceWithUser()<chosenDistance) {
                for (int j = 0 ; j<storeList.get(i).getStoreCategories().size() ;j++){
                    if (storeList.get(i).getStoreCategories().get(j).equals(chosenCategorie) || chosenCategorie.equals(null)){
                        mMap.addMarker(new MarkerOptions().position(storeList.get(i).getStoreCoordinates()).title(storeList.get(i).getStoreName()).snippet(storeList.get(i).getStoreCampaign()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(storeList.get(i).getStoreCoordinates()));
                    }
                }
            }
        }

        allChoicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<storeList.size();i++){
                        mMap.addMarker(new MarkerOptions().position(storeList.get(i).getStoreCoordinates()).title(storeList.get(i).getStoreName()).snippet(storeList.get(i).getStoreCampaign()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(storeList.get(i).getStoreCoordinates()));
                }
            }
        });
        mMap.setMyLocationEnabled(true);
    }

}
