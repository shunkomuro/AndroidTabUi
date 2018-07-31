package com.example.komuroshun.androidtabui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.FrameLayout;

public class CustomBottomTabContentView extends FrameLayout {
    LayoutInflater inflater = null;

    public CustomBottomTabContentView(Context context) {
        super(context);
        inflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomBottomTabContentView(Context context, String title, int icon) {
        this(context);
        inflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View childview = inflater.inflate(R.layout.widget_bottom_tab, null);
        TextView tv = (TextView) childview.findViewById(R.id.textview);
        ImageView iv = (ImageView) childview.findViewById(R.id.imageview);
        tv.setText(title);
        iv.setImageResource(icon);
        addView(childview);
    }
}
