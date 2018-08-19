package com.example.komuroshun.androidtabui.card;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.komuroshun.androidtabui.R;

public class CardListAdapter extends ArrayAdapter<PackageInfo> {

    LayoutInflater mLayoutInflater;
    PackageManager mPackageManager;
    private int mLastAnimationPosition;

    public CardListAdapter(Context context) {
        super(context, 0);
        mLayoutInflater = LayoutInflater.from(context);
        mPackageManager = context.getPackageManager();
    }

    // 表示するリストアイテムのビューを返却する
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_card, parent, false);
        }

        PackageInfo mPackageInfo = getItem(position);

        // タイトル
        TextView mItemTitleTextView = (TextView) convertView.findViewById(R.id.title);
        mItemTitleTextView.setText(mPackageInfo.applicationInfo.loadLabel(mPackageManager));

        // パッケージ名など
        TextView mItemPackageInfoTextView = (TextView) convertView.findViewById(R.id.sub);
        mItemPackageInfoTextView.setText(mPackageInfo.packageName + System.getProperty("line.separator")
                + "versionName : " + mPackageInfo.versionName+ System.getProperty("line.separator")
                + "versionCode : " + mPackageInfo.versionCode);

        // アイコン
        ImageView mItemIconImageView = (ImageView) convertView.findViewById(R.id.icon);
        mItemIconImageView.setImageDrawable(mPackageInfo.applicationInfo.loadIcon(mPackageManager));

        if (mLastAnimationPosition < position) {
            Animation mCardAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.motion);
            convertView.startAnimation(mCardAnimation);
            mLastAnimationPosition = position;
        }

        return convertView;
    }
}