package com.example.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faruqtraders.API.RetrofitClient;
import com.example.faruqtraders.MainActivity;
import com.example.faruqtraders.Response.UserRegisterResponse;
import com.example.faruqtraders.R;
import com.example.faruqtraders.Utility.NetworkChangeListener;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    EditText fullNameEditText, emailEditText, numberEditText, passwordEditText, confirmPasswordEditText;
    TextView haveAccountTextView;
    AppCompatButton signUpButton;
    CircleImageView facebookImage, googleImage;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialization();
        setListener();

    }

    private void initialization(){
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.signUpEmailEditText);
        numberEditText = findViewById(R.id.phoneNumberEditText);
        passwordEditText = findViewById(R.id.signupPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.signupConfirmPasswordEditText);

        haveAccountTextView = findViewById(R.id.haveAccountTextView);
        signUpButton = findViewById(R.id.signupButton);

        progressBar = findViewById(R.id.progressBar);

    }

    private void setListener(){
        haveAccountTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.haveAccountTextView:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;

            case R.id.signupButton:
                SignupNewUser();
                break;

        }
    }

    private void SignupNewUser(){

        String name = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String number = numberEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        System.out.println("Registration Name is ============== > " + name);

        if (name.isEmpty()){
            showToast("Enter Name");
            return;
        }

        else if (email.isEmpty()){
            showToast("Enter Email");
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString().trim()).matches()){
            showToast("Enter valid Email");
            return;
        }
        else if (number.isEmpty()){
            showToast("Enter Number");
            return;
        }
        else if (password.isEmpty()){
            showToast("Enter Password");
            return;
        }
        else if (password.length() < 6){
            showToast("Minimum Password is 6 ");
            return;
        }

        else if (confirmPassword.isEmpty()){
            showToast("Write Confirm Password");
            return;
        }
        else if (!password.equals(confirmPassword)){
            showToast("Password and Confirm Password Should be Same");
            return;
        }
        else {
            callApi(name, email, number, password, confirmPassword,"" );
            showToast("call Api");
        }
    }

    private void callApi(String name, String email, String phone, String password, String confirmPassword, String device_name) {

        signUpButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getRetrofitClient().createUser(name, email, phone, password, confirmPassword, device_name).enqueue(new Callback<UserRegisterResponse>() {

            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {

                if (response.isSuccessful()){

                    signUpButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                    showToast("Account Created Successfully...");

                    fullNameEditText.setText("");
                    emailEditText.setText("");
                    numberEditText.setText("");
                    passwordEditText.setText("");
                    confirmPasswordEditText.setText("");

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                    startActivity(intent);

                }
                else {
                    signUpButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    String errorMessage = response.errorBody().toString();
                    showToast(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                signUpButton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                showToast("Failure ....."+t.getLocalizedMessage());
                Log.e("TAG", t.getLocalizedMessage());
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}