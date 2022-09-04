package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.binaryit.faruqtraders.API.RetrofitClientWithHeader;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.ResetPasswordResponse;
import com.binaryit.faruqtraders.Session.SessionManagement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageView imageBack;
    AppCompatButton resetPasswordButton;
    EditText oldPassword, newPassword, confirmPassword;

    String oldPasswordString, newPasswordString, confirmPasswordString;

    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initialization();
        setListener();

        /*Here will have to receive password from signup activity to confirm old password*/

    }

    private void initialization(){

        sessionManagement = new SessionManagement(this);

        imageBack = findViewById(R.id.imageBack);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        oldPassword = findViewById(R.id.oldPasswordEditText);
        newPassword = findViewById(R.id.newPasswordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);

    }

    private void setListener(){
        imageBack.setOnClickListener(this);
        resetPasswordButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                break;
            case R.id.resetPasswordButton:
                validation();
        }
    }

    private void validation() {

        oldPasswordString = oldPassword.getText().toString().trim();
        newPasswordString = newPassword.getText().toString().trim();
        confirmPasswordString = confirmPassword.getText().toString().trim();

        if (oldPasswordString.isEmpty()){
            showToast("Enter Old Password");
            return;
        }

        /*Here have to check old password*/

        if (newPasswordString.isEmpty()){
            showToast("Enter New Password");
            return;
        }

        if (confirmPasswordString.isEmpty()){
            showToast("Enter New Password");
            return;
        }
        if (newPasswordString.equals(oldPasswordString)){
            showToast("New password can not be same like old password");
            return;
        }

        if (!newPasswordString.equals(confirmPasswordString)) {
            showToast("New Password and Confirm Password Should be Same");
            return;
        }

        else {
            resetPassword();
        }


    }

    private void resetPassword(){
        RetrofitClientWithHeader.getRetrofitClient(this).resetPassword(oldPasswordString, newPasswordString, confirmPasswordString).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if (response.body() != null && response.isSuccessful()){
                    showToast("Password Changed");
                    sessionManagement.removeLoginSession();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}