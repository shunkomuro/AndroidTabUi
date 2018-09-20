package com.example.komuroshun.androidtabui.card

import android.content.Context
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter

import com.example.komuroshun.androidtabui.R
import com.example.komuroshun.androidtabui.databinding.ItemCardBinding

class CardListAdapter(context: Context) : ArrayAdapter<CardItemViewModel>(context, 0) {

    internal var mLayoutInflater: LayoutInflater
    internal var mPackageManager: PackageManager
    private var mLastAnimationPosition: Int = 0

    init {
        mLayoutInflater = LayoutInflater.from(context)
        mPackageManager = context.packageManager
    }

    // 表示するリストアイテムのビューを返却する
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemCardBinding

        var convertView = convertView
        if (convertView == null) {
            val inflater: LayoutInflater = LayoutInflater.from(getContext())
            binding = DataBindingUtil.inflate(inflater, R.layout.item_card, parent, false)
            convertView = binding.root
            convertView.setTag(binding)
        } else {
            binding = convertView.getTag() as ItemCardBinding
        }

        binding.setViewModel(getItem(position))

        if (mLastAnimationPosition < position) {
            val mCardAnimation = AnimationUtils.loadAnimation(context, R.anim.motion)
            convertView.startAnimation(mCardAnimation)
            mLastAnimationPosition = position
        }

        return binding.root
    }
}