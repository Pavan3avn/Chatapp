package com.pavan.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class loginactivity extends AppCompatActivity {

    CountryCodePicker countrycode;
    Button btn;
    EditText usernum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        countrycode = findViewById(R.id.countrycode);
        btn = findViewById(R.id.btn);
        usernum = findViewById(R.id.mobilenum);

        countrycode.registerCarrierNumberEditText(usernum);
        btn.setOnClickListener(view -> {
            if(!countrycode.isValidFullNumber()){
                Toast.makeText(loginactivity.this,"Mobile number not valid",Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(loginactivity.this,otpactivity.class);
                intent.putExtra("phone",countrycode.getFullNumberWithPlus());
                startActivity(intent);

            }

        });
    }
}