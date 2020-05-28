package com.example.locationbasedcampaign.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.locationbasedcampaign.AdminPanelActivity;
import com.example.locationbasedcampaign.R;
import com.example.locationbasedcampaign.User;

import java.util.List;

import static com.example.locationbasedcampaign.GlobalVariables.userList;

public class LoginActivity extends AppCompatActivity {

    EditText usrName,pass;
    Button confirm,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createElements();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName,password;
                 userName = usrName.getText().toString();
                 password = pass.getText().toString();

                 if (userName.equals("")){
                     Toast.makeText(LoginActivity.this,"Kullanıcı adı gerekli",Toast.LENGTH_SHORT).show();
                 }else if (password.equals("")){
                    Toast.makeText(LoginActivity.this,"Şifre gerekli",Toast.LENGTH_SHORT).show();
                }else if (userName.equals("admin")){
                     if (password.equals("admin123")){
                         openAdminPanelActivity();
                     }else{
                         Toast.makeText(LoginActivity.this,"Admin şifresi gerekli",Toast.LENGTH_SHORT).show();
                     }
                 }else {
                     int userIndex = searchName(userList,userName);
                     if (userIndex >= 0){
                         if (checkPass(userList,password,userIndex)){
                            openMainActivity();
                         }else{
                             Toast.makeText(LoginActivity.this,"Şifre yanlış",Toast.LENGTH_SHORT).show();
                         }
                     }else{
                         Toast.makeText(LoginActivity.this,"Geçersiz kullanıcı adı",Toast.LENGTH_SHORT).show();
                     }
                 }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    public static int searchName(List<User> users, String value){
        for (int i=0; i < users.size() ; i++){
            if (users.get(i).getUsr_Name().equals(value)){
                return i;
            }
        }
        return -1;
    }

    public static boolean checkPass(List<User> users, String value, int i){
        return users.get(i).getUsr_Pass().equals(value);
    }

    private void openMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    private void openRegisterActivity() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
    private void openAdminPanelActivity() {
        Intent i = new Intent(this, AdminPanelActivity.class);
        startActivity(i);
    }

    public void createElements(){
        usrName = (EditText)findViewById(R.id.login_usrname_editText);
        pass = (EditText)findViewById(R.id.login_pass_editext);
        confirm = (Button)findViewById(R.id.login_confirm_btn);
        register = (Button)findViewById(R.id.login_register_btn);
    }
}
