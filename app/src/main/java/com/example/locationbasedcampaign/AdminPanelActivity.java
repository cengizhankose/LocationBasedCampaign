package com.example.locationbasedcampaign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import static com.example.locationbasedcampaign.GlobalVariables.storeList;

public class AdminPanelActivity extends AppCompatActivity {
    EditText storeName,storeCampaign,storeCoordinatesX,storeCoordinatesY;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        createElements();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, campaign;
                Double coordinatesX;
                Double coordinatesY;
                LatLng coordinates;
                name = storeName.getText().toString();
                campaign = storeCampaign.getText().toString();
                coordinatesX = Double.parseDouble(storeCoordinatesX.getText().toString());
                coordinatesY = Double.parseDouble(storeCoordinatesY.getText().toString());
                coordinates = new LatLng(coordinatesX,coordinatesY);

                if (name.equals("")){
                    Toast.makeText(AdminPanelActivity.this,"Sinema Salonunun adı gerekli",Toast.LENGTH_SHORT).show();
                }else if (campaign.equals("")){
                    Toast.makeText(AdminPanelActivity.this,"Kampanyaları girmeniz gerekli",Toast.LENGTH_SHORT).show();
                }else if (coordinatesX.equals("")){
                    Toast.makeText(AdminPanelActivity.this,"koordinatlarını girmeniz gerekli",Toast.LENGTH_SHORT).show();
                }else if (coordinatesY.equals("")){
                    Toast.makeText(AdminPanelActivity.this,"koordinatlarını girmeniz gerekli",Toast.LENGTH_SHORT).show();
                }else {
                    addStore(name, coordinates, campaign);
                    Toast.makeText(AdminPanelActivity.this,"Sinema Salonu eklendi",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void addStore(String name, LatLng coordinates, String campaign){
        Store u = new Store(name, coordinates, campaign);
        storeList.add(u);
    }
    public void createElements(){
        storeName=(EditText)findViewById(R.id.admin_name_editText);
        storeCampaign=(EditText)findViewById(R.id.admin_campaign_editext);
        storeCoordinatesX=(EditText)findViewById(R.id.admin_coordinatesX_editText);
        storeCoordinatesY=(EditText)findViewById(R.id.admin_coordinatesY_editText);
        confirm=(Button)findViewById(R.id.admin_confirm_btn);
    }
}
