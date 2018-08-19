package com.example.komuroshun.androidtabui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import android.widget.FrameLayout

class CustomBottomTabContentView(context: Context) : FrameLayout(context) {
    internal var inflater: LayoutInflater? = null

    init {
        inflater = context.applicationContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    constructor(context: Context, title: String, icon: Int) : this(context) {
        inflater = context.applicationContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val childview = inflater!!.inflate(R.layout.widget_bottom_tab, null)
        val tv = childview.findViewById<View>(R.id.textview) as TextView
        val iv = childview.findViewById<View>(R.id.imageview) as ImageView
        tv.text = title
        iv.setImageResource(icon)
        addView(childview)
    }
}
