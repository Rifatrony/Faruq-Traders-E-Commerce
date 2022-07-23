package com.binaryit.faruqtraders.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.binaryit.faruqtraders.R;
import com.binaryit.faruqtraders.Response.BannerResponse;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    Context context;
    BannerResponse bannerResponse;

    public BannerAdapter(Context context, BannerResponse bannerResponse) {
        this.context = context;
        this.bannerResponse = bannerResponse;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_layout, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        Glide.with(context).load(bannerResponse.data.get(position).image).into(holder.bannerImage);
    }

    @Override
    public int getItemCount() {
        return bannerResponse.data.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView bannerImage;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);

            bannerImage = itemView.findViewById(R.id.bannerImage);

        }
    }

}
