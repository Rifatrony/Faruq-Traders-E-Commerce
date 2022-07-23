package com.binaryit.faruqtraders.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryit.faruqtraders.R;

public class CategoryGridAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    String[] numberWord;
    int[] numberImage;

    public CategoryGridAdapter(Context context, LayoutInflater inflater, String[] numberWord, int[] numberImage) {
        this.context = context;
        this.inflater = inflater;
        this.numberWord = numberWord;
        this.numberImage = numberImage;
    }

    @Override
    public int getCount() {
        return numberWord.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null){
            view = inflater.inflate(R.layout.all_category_layout, null);

        }

        ImageView imageView = view.findViewById(R.id.category_image_view);
        TextView textView = view.findViewById(R.id.category_name_text_view);

        imageView.setImageResource(numberImage[i]);
        textView.setText(numberWord[i]);

        return view;
    }
}
