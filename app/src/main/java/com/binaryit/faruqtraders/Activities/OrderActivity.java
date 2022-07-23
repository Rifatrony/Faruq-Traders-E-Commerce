package com.binaryit.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.binaryit.faruqtraders.R;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageView imageBack;
    RecyclerView orderRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initialization();

        setListener();
    }

    private void setListener() {
        imageBack.setOnClickListener(this);
    }

    private void initialization() {
        imageBack = findViewById(R.id.imageBack);
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageBack:
                onBackPressed();
                break;

        }
    }
}