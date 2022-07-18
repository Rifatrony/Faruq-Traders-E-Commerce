package com.example.faruqtraders.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.faruqtraders.Model.ImageModel;
import com.example.faruqtraders.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<ImageModel> imageModelList;
    private ViewPager2 viewPager2;

    public ImageAdapter(List<ImageModel> imageModelList, ViewPager2 viewPager2) {
        this.imageModelList = imageModelList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_container, parent, false);

        return new ImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.roundedImageView.setImageResource(imageModelList.get(position).getImage());

        if (position == imageModelList.size()-2){
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return imageModelList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView roundedImageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView = itemView.findViewById(R.id.roundedImageView);

        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            imageModelList.addAll(imageModelList);
            notifyDataSetChanged();
        }
    };

}
