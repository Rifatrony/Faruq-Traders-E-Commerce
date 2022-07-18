package com.example.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.faruqtraders.R;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageView imageBack;
    AppCompatButton resetPasswordButton;
    EditText oldPassword, newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initialization();
        setListener();

        /*Here will have to receive password from signup activity to confirm old password*/

    }

    private void initialization(){
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

        String oldPasswordString = oldPassword.getText().toString().trim();
        String newPasswordString = newPassword.getText().toString().trim();
        String confirmPasswordString = confirmPassword.getText().toString().trim();

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

        if (!newPasswordString.equals(confirmPasswordString)) {

            showToast("Password and Confirm Password Should be Same");
            return;

        }

        else {
            resetPassword();
        }


    }

    private void resetPassword(){
        showToast("Password Changed");
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}