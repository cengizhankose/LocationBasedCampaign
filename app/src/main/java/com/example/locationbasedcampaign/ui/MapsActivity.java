package com.example.locationbasedcampaign.ui;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.locationbasedcampaign.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import static com.example.locationbasedcampaign.GlobalVariables.storeList;
import static com.example.locationbasedcampaign.GlobalVariables.chosenDistance;
import static com.example.locationbasedcampaign.GlobalVariables.category;
import static com.example.locationbasedcampaign.GlobalVariables.userList;

public class MapsActivity<allChoicesBtn> extends FragmentActivity implements OnMapReadyCallback {

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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i=0; i<storeList.size();i++){
            if(storeList.get(i).getStoreDistanceWithUser()<chosenDistance) {
                mMap.addMarker(new MarkerOptions().position(storeList.get(i).getStoreCoordinates()).title(storeList.get(i).getStoreName()).snippet(storeList.get(i).getStoreCampaign()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(storeList.get(i).getStoreCoordinates()));
            }
        }
/*
        LatLng kardiyum = new LatLng(41.031935, 29.227113);
        mMap.addMarker(new MarkerOptions().position(kardiyum).title("Kardiyum AVM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kardiyum));

        LatLng sancakpark = new LatLng(41.010334, 29.210970);
        mMap.addMarker(new MarkerOptions().position(sancakpark).title("Sancakpark AVM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sancakpark));

        LatLng osmanli = new LatLng(41.007910, 29.243073);
        mMap.addMarker(new MarkerOptions().position(osmanli).title("Sancaktepe Osmanlı Çarşı"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(osmanli));

        chosenDistance = SphericalUtil.computeDistanceBetween(kardiyum,sancakpark);
        Toast.makeText(this, chosenDistance/1000+"km", Toast.LENGTH_SHORT).show();
*/
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
