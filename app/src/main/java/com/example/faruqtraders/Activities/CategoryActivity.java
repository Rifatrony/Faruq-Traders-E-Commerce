package com.example.faruqtraders.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.faruqtraders.Adapter.CategoryGridAdapter;
import com.example.faruqtraders.R;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    String[] numberWord = {"Biscuits & Chips","Juice & Drinks","Oil & Etc",
            "Cleaning & Supplies","Chocolate & More","Nuts & Dates", "Baking & Cooking",
            "Oatas & Corn Flakes", "Diapers Wipes", "Honey & Jam Spread", "Milk",
            "Diabetic Care", "Coffee & Tea", "Baby Food & Milk Item", "Noodles & Pasta",
            "Sauce & Mayonnaise", "Frozen Product", "Skin Care"};

    int[] numberImages = {R.drawable.biscuits,R.drawable.juice,R.drawable.oil,
            R.drawable.clean,R.drawable.chocolate,R.drawable.nut ,R.drawable.baking,
            R.drawable.oatas, R.drawable.diapers, R.drawable.jam, R.drawable.milk,
            R.drawable.diabetic, R.drawable.tea, R.drawable.baby_food, R.drawable.pasta,
            R.drawable.sauce, R.drawable.frozen, R.drawable.skin};

    LayoutInflater layoutInflater;

    AppCompatImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initialization();
        setListener();

        CategoryGridAdapter categoryGridAdapter = new CategoryGridAdapter(CategoryActivity.this,layoutInflater ,numberWord, numberImages);
        gridView.setAdapter(categoryGridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), EachCategoryActivity.class);

                intent.putExtra("name", numberWord[i]);
                startActivity(intent);
                //System.out.println("In Name========>" + numberWord[i]);
                Toast.makeText(CategoryActivity.this, "You have clicked " + numberWord[+i], Toast.LENGTH_SHORT).show();
                //Toast.makeText(CategoryActivity.this, "You clicked " + i, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initialization() {

        gridView = findViewById(R.id.grid_view);
        imageView = findViewById(R.id.category_image_back);

    }

    private void setListener(){
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.category_image_back:
                onBackPressed();
                break;

            default:
                return;
        }
    }
}