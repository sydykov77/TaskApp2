package com.example.taskapp2.ui.onBoard;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.viewpager.widget.PagerAdapter;

import com.example.taskapp2.R;
import com.example.taskapp2.models.Models;


public class PageAdapter extends PagerAdapter {


    public interface onStartClickLListener {
        void onStart();
    }

    private onStartClickLListener onStartClickLListener;


    void setOnStartClickLListener(PageAdapter.onStartClickLListener onStartClickLListener) {
        this.onStartClickLListener = onStartClickLListener;
    }

    private Models[] models = new Models[]{
            new Models(R.drawable.mercedes, "Mercedes Benz", "AMG")
            , new Models(R.drawable.bmw, "BMW", "M//sport")
            , new Models(R.drawable.lamborghini, "Lamborghini", "Aventador")};

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.page_onboard, container, false);
        onBindPageOnBoard(view, position);
        setOnClickListeners(view);
        container.addView(view);
        return view;
    }


    private void setOnClickListeners(View view) {
        view.findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartClickLListener.onStart();
            }
        });
    }

    private void onBindPageOnBoard(View view, int position) {
        ((TextView) view.findViewById(R.id.textTitle)).setText(models[position].getTitle());
        ((TextView) view.findViewById(R.id.textDesc)).setText(models[position].getDescription());
        ((ImageView) view.findViewById(R.id.imageView)).setImageResource(models[position].getImage());
        if (position < 2) {
            view.findViewById(R.id.btnStart).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.btnStart).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
