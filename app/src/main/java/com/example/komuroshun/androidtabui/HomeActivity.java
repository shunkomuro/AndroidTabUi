package com.example.komuroshun.androidtabui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

/**
 * Activities manage fragments.
 * @author Shun Komuro
 * @version 1.0
 */
public class HomeActivity extends AppCompatActivity implements FragmentTabHost.OnTabChangeListener,
        PageFragment.OnFragmentInteractionListener,
        TabContainerFragment.OnFragmentInteractionListener {

    private static final String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.container);

        TabHost.TabSpec tabSpec1, tabSpec2, tabSpec3, historyTabSpec, tabSpec5;

        // タブ生成1
        tabSpec1 = tabHost.newTabSpec("tab1");
        tabSpec1.setIndicator("tab1");
        // クラス調査使い方
        Bundle bundle1 = new Bundle();
        bundle1.putString("name", "tab1");
        tabHost.addTab(tabSpec1, PageFragment.class, bundle1);
        // タブ生成2
        tabSpec2 = tabHost.newTabSpec("tab2");
        tabSpec2.setIndicator("tab2");
        Bundle bundle2 = new Bundle();
        bundle2.putString("name", "tab2");
        tabHost.addTab(tabSpec2, PageFragment.class, bundle2);
        // タブ生成3
        tabSpec3 = tabHost.newTabSpec("tab3");
        tabSpec3.setIndicator("tab3");
        Bundle bundle3 = new Bundle();
        bundle3.putString("name", "tab3");
        tabHost.addTab(tabSpec3, PageFragment.class, bundle3);

        historyTabSpec = tabHost.newTabSpec("履歴");
        historyTabSpec.setIndicator("履歴");
        Bundle bundle4 = new Bundle();
        bundle4.putString("name", "履歴");
        tabHost.addTab(historyTabSpec, TabContainerFragment.class, bundle4);

        tabSpec5 = tabHost.newTabSpec("6Pack");
        tabSpec5.setIndicator("6Pack");
        Bundle bundle5 = new Bundle();
        bundle5.putString("name", "6Pack");
        tabHost.addTab(tabSpec5, TabContainerFragment.class, bundle5);

        // リスナー登録
        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {
    }

    /**
     * Switch Fragments only.
     * @param uri Uri
     * @author Shun Komuro
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
