package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClient;
import com.binaryit.faruqtraders.Model.SessionDataModel;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.UserRegisterResponse;
import com.binaryit.faruqtraders.Session.SessionManagement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText otpEditText;
    String name, email, phone, password, confirmPassword, device_name;
    AppCompatButton verifyOPTButton;
    ProgressBar verifyOtpProgressBar;

    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initialization();
        setListener();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        confirmPassword = getIntent().getStringExtra("confirmPassword");
        device_name = getIntent().getStringExtra("device_name");

        System.out.println("\n\nName is " + name);
        System.out.println("email is " + email);
        System.out.println("phone is " + phone);
        System.out.println("password is " + password);
        System.out.println("confirmPassword is " + confirmPassword);
        System.out.println("device_name is " + device_name);

    }

    private void initialization() {
        otpEditText = findViewById(R.id.otpEditTextId);
        verifyOPTButton = findViewById(R.id.verifyOPTButton);
        verifyOtpProgressBar = findViewById(R.id.verifyOtpProgressBar);
        sessionManagement = new SessionManagement(this);
    }

    private void setListener() {
        verifyOPTButton.setOnClickListener(this);
    }

    private void CreateNewUser(){

        verifyOtpProgressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getRetrofitClient().createUser(name, email, phone, password, confirmPassword, device_name, otpEditText.getText().toString().trim()).enqueue(new Callback<UserRegisterResponse>() {

            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {

                if (response.isSuccessful()){

                    verifyOPTButton.setVisibility(View.VISIBLE);
                    verifyOtpProgressBar.setVisibility(View.INVISIBLE);

                    showToast("Otp Verified and Account Created Successfully...");

                    /*fullNameEditText.setText("");
                    emailEditText.setText("");
                    numberEditText.setText("");
                    passwordEditText.setText("");
                    confirmPasswordEditText.setText("");*/

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    SessionDataModel dataModel = new SessionDataModel(response.body().getAccess_token(), email, password);
                    sessionManagement.setLoginSession(dataModel);
                    startActivity(intent);

                }
                else {
                    verifyOPTButton.setVisibility(View.VISIBLE);
                    verifyOtpProgressBar.setVisibility(View.INVISIBLE);
                    String errorMessage = response.errorBody().toString();
                    showToast(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                verifyOPTButton.setVisibility(View.VISIBLE);
                verifyOtpProgressBar.setVisibility(View.INVISIBLE);
                showToast("Failure ....."+t.getLocalizedMessage());
                Log.e("TAG", t.getLocalizedMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.verifyOPTButton:
                CreateNewUser();
                break;
        }
    }
}