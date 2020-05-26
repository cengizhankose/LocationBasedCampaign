package com.example.locationbasedcampaign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.locationbasedcampaign.ui.MainActivity;

import static com.example.locationbasedcampaign.GlobalVariables.userList;

public class RegisterActivity extends AppCompatActivity {
    EditText usrName,usrEmail,usrPass,usrConfirmPass;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createElements();

        confirm.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "RegisterActivity";
            @Override
            public void onClick(View v) {
                String userName, email, password, confirmPassword;
                userName = usrName.getText().toString();
                email = usrEmail.getText().toString();
                password = usrPass.getText().toString();
                confirmPassword = usrConfirmPass.getText().toString();

                if (userName.equals("")){
                    Toast.makeText(RegisterActivity.this,"Kullanıcı adı gerekli",Toast.LENGTH_SHORT).show();
                }else if (email.equals("")){
                    Toast.makeText(RegisterActivity.this,"E-mail gerekli",Toast.LENGTH_SHORT).show();
                }else if (password.equals("")){
                    Toast.makeText(RegisterActivity.this,"Şifre gerekli",Toast.LENGTH_SHORT).show();
                }else if (confirmPassword.equals("")){
                    Toast.makeText(RegisterActivity.this,"Şifre gerekli",Toast.LENGTH_SHORT).show();
                }else if (!(confirmPassword.equals(password))){
                    Toast.makeText(RegisterActivity.this,"Şifreler uyuşmalı",Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(userName, email, password);
                    openLoginActivity();
                }
            }
        });
    }

     public void registerUser(String username,String email,String password){
        User u = new User(username, email, password);
        userList.add(u);
     }

    private void openLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void createElements(){
        usrName=(EditText)findViewById(R.id.register_usrname_editText);
        usrEmail=(EditText)findViewById(R.id.register_mail_editText);
        usrPass=(EditText)findViewById(R.id.register_pass_editext);
        usrConfirmPass=(EditText)findViewById(R.id.register_confirmPass_editext);
        confirm=(Button)findViewById(R.id.register_confirm_btn);
    }
}
