package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.binaryit.faruqtraders.R;

import java.util.regex.Pattern;

public class LostPasswordActivity extends AppCompatActivity {

    EditText emailEditText;
    AppCompatButton submitButton, okButton;
    ProgressBar progressBar;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_password);

        emailEditText = findViewById(R.id.emailEditText);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailEditText.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(LostPasswordActivity.this, "Enter a email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(LostPasswordActivity.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    forgetPassword();
                }
            }
        });

    }

    private void forgetPassword() {
        /*progressBar.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.INVISIBLE);*/

        Dialog dialog = new Dialog(LostPasswordActivity.this);
        dialog.setContentView(R.layout.success_pop_up_layout);
        okButton = dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}