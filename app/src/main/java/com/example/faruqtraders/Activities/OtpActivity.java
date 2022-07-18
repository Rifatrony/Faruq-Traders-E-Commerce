package com.example.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.faruqtraders.R;

public class OtpActivity extends AppCompatActivity {

    EditText otpEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initialization();
        setListener();

    }

    private void initialization() {
        otpEditText = findViewById(R.id.otpEditTextId);
    }

    private void setListener() {

    }

}