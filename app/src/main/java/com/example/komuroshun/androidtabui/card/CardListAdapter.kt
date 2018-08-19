package com.example.komuroshun.androidtabui.card

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import com.example.komuroshun.androidtabui.R

class CardListAdapter(context: Context) : ArrayAdapter<PackageInfo>(context, 0) {

    internal var mLayoutInflater: LayoutInflater
    internal var mPackageManager: PackageManager
    private var mLastAnimationPosition: Int = 0

    init {
        mLayoutInflater = LayoutInflater.from(context)
        mPackageManager = context.packageManager
    }

    // 表示するリストアイテムのビューを返却する
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_card, parent, false)
        }

        val mPackageInfo = getItem(position)

        // タイトル
        val mItemTitleTextView = convertView!!.findViewById<View>(R.id.title) as TextView
        mItemTitleTextView.text = mPackageInfo!!.applicationInfo.loadLabel(mPackageManager)

        // パッケージ名など
        val mItemPackageInfoTextView = convertView.findViewById<View>(R.id.sub) as TextView
        mItemPackageInfoTextView.text = (mPackageInfo.packageName + System.getProperty("line.separator")
                + "versionName : " + mPackageInfo.versionName + System.getProperty("line.separator")
                + "versionCode : " + mPackageInfo.versionCode)

        // アイコン
        val mItemIconImageView = convertView.findViewById<View>(R.id.icon) as ImageView
        mItemIconImageView.setImageDrawable(mPackageInfo.applicationInfo.loadIcon(mPackageManager))

        if (mLastAnimationPosition < position) {
            val mCardAnimation = AnimationUtils.loadAnimation(context, R.anim.motion)
            convertView.startAnimation(mCardAnimation)
            mLastAnimationPosition = position
        }

        return convertView
    }
}