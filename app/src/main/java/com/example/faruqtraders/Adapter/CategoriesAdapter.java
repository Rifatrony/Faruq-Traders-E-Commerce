package com.example.faruqtraders.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faruqtraders.Activities.CategoryWiseProductActivity;
import com.example.faruqtraders.Model.CategoryModel;
import com.example.faruqtraders.R;
import com.example.faruqtraders.Response.ApiResponseModel;
import com.example.faruqtraders.Response.CategoryResponseModel;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    Context context;
    public static CategoryResponseModel data;

    public CategoriesAdapter(Context context, CategoryResponseModel data) {
        this.context = context;
        this.data = data;
    }

    public CategoriesAdapter() {
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_category_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (data.categories.data.size() > 0 ){
            holder.name.setText(data.categories.data.get(position).name);
            Glide.with(context).load(data.categories.data.get(position).image).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CategoryWiseProductActivity.class);
                    intent.putExtra("slug", data.categories.data.get(position).slug);
                    intent.putExtra("name", data.categories.data.get(position).name);
                    intent.putExtra("icon", data.categories.data.get(position).icon);
                    context.startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.categories.data.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.category_name_text_view);
            imageView = itemView.findViewById(R.id.category_image_view);

        }
    }

}