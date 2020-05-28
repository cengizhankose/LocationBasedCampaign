package com.example.locationbasedcampaign.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationbasedcampaign.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import static com.example.locationbasedcampaign.AdminPanelActivity.addStore;
import static com.example.locationbasedcampaign.Constants.ERROR_DIALOG_REQUEST;
import static com.example.locationbasedcampaign.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.locationbasedcampaign.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;
import static com.example.locationbasedcampaign.GlobalVariables.storeList;
import static com.example.locationbasedcampaign.GlobalVariables.chosenDistance;
import static com.example.locationbasedcampaign.GlobalVariables.chosenCategorie;
import static com.example.locationbasedcampaign.GlobalVariables.category;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Button mapBtn,categoryBtn,distanceBtn;
    TextView categoryTxt,distanceTxt;
    String[] categorieArray,distanceArray;
    boolean mLocationPermissionGranted=false;
    public LatLng userLocation;
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createElements();
        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExistingStores();
                Log.d(TAG, "onClick: kategoriler - "+storeList.get(1).getStoreCategories());
                getLastKnownLocation();
                openMapsActivity();
            }
        });

        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorieArray = new String[]{"Korku", "Aksiyon", "Macera", "Animasyon", "Çocuk", "Komedi"};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("Seciminizi Yapiniz");
                mBuilder.setIcon(R.drawable.icon);
                mBuilder.setSingleChoiceItems(categorieArray, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        categoryTxt.setText(categorieArray[which]);
                        chosenCategorie = categorieArray[which];
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Iptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        distanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceArray = new String[]{"1", "2", "4", "8", "10"};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("Seciminizi Yapiniz (km)");
                mBuilder.setIcon(R.drawable.icon);
                mBuilder.setSingleChoiceItems(distanceArray, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        distanceTxt.setText(distanceArray[which]);
                        chosenDistance = Double.parseDouble(distanceArray[which]);
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Iptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }


    public void getLastKnownLocation(){
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()){
                    Location location = task.getResult();
                    userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    calculateDistanceBetweenStores(userLocation);
                }
            }
        });
    }

    public void calculateDistanceBetweenStores(LatLng location) {
        for (int i = 0;i<storeList.size();i++){
            double d = 0;
            d = (SphericalUtil.computeDistanceBetween(location,storeList.get(i).getStoreCoordinates())/1000);
            storeList.get(i).setStoreDistanceWithUser(d);
        }
    }



    public void addExistingStores(){
        String storeNames[] = {"Kardiyum AVM", "Sancakpark AVM", "Sancaktepe Osmanlı Çarşı"};
        LatLng storeCoordinates[] = {new LatLng(41.031935, 29.227113),new LatLng(41.010334, 29.210970),new LatLng(41.007910, 29.243073)};
        String storeCampaigns[] = {"Bir bilet alana ikinci yarı fiyatına","Bir bilet alana ikinci bedava","Bir bilet alana ikinci iki katına"};
        String storeCategories[][] = {{"Korku","Aksiyon","Macera"},{"Aksiyon","Macera","Animasyon"},{"Animasyon","Çocuk","Komedi"}};
        for(int i = 0; i<storeNames.length;i++){
            addStore(storeNames[i],storeCoordinates[i],storeCampaigns[i]);
        }
        for (int i = 0; i<storeNames.length;i++){
            for (int j = 0; j<storeCategories[i].length;j++){
                storeList.get(i).addStoreCategorie(storeCategories[i][j]);
            }
        }
    }


    private void openMapsActivity() {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                }
                else{
                    getLocationPermission();
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if (mLocationPermissionGranted){

            }else{
                getLocationPermission();
            }
        }
    }

    public void createElements(){
        mapBtn=(Button)findViewById(R.id.mapBtn);
        categoryBtn=(Button)findViewById(R.id.categoryBtn);
        distanceBtn=(Button)findViewById(R.id.distanceBtn);
        categoryTxt=(TextView)findViewById(R.id.categoryTxt);
        distanceTxt=(TextView)findViewById(R.id.distanceTxt);
    }
}
