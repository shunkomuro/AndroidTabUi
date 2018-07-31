package com.example.komuroshun.androidtabui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;

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

        // Toolbar Settings
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Spinner spinner = (Spinner) toolbar.findViewById(R.id.tool_bar_spinner);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedName = String.valueOf(parent.getSelectedItem());
//                Bundle selectedNameBundle = new Bundle();
//                selectedNameBundle.putString("name", selectedName);
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                Fragment tabContainerFragment = new TabContainerFragment();
//                tabContainerFragment.setArguments(selectedNameBundle);
//                fragmentTransaction.replace(R.id.container, tabContainerFragment);
//                fragmentTransaction.addToBackStack(null); // 戻るボタンでreplace前に戻る
//                fragmentTransaction.commit();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
        spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.fragment_names,
                android.R.layout.simple_spinner_dropdown_item));

        FragmentTabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.container);

        // 画面下部タブメニュー
        TabHost.TabSpec tabHome, tabNotice, tabPoint, tabHistory, tabOthers;
        // タブ生成1
        View tabViewHome = new CustomBottomTabContentView(this, "ホーム", R.drawable.selector_bottom_tab_home);
        tabHome = tabHost.newTabSpec("ホーム");
        tabHome.setIndicator(tabViewHome);
        // クラス調査使い方
        Bundle bundle1 = new Bundle();
        bundle1.putString("name", "ホーム");
        tabHost.addTab(tabHome, PageFragment.class, bundle1);
        // タブ生成2
        View tabViewNotice = new CustomBottomTabContentView(this, "お知らせ", R.drawable.selector_bottom_tab_notice);
        tabNotice = tabHost.newTabSpec("お知らせ");
        tabNotice.setIndicator(tabViewNotice);
        Bundle bundle2 = new Bundle();
        bundle2.putString("name", "お知らせ");
        tabHost.addTab(tabNotice, PageFragment.class, bundle2);
        // タブ生成3
        View tabViewPoint = new CustomBottomTabContentView(this, "ポイント", R.drawable.selector_bottom_tab_point);
        tabPoint = tabHost.newTabSpec("ポイント");
        tabPoint.setIndicator(tabViewPoint);
        Bundle bundle3 = new Bundle();
        bundle3.putString("name", "ポイント");
        tabHost.addTab(tabPoint, PageFragment.class, bundle3);

        View tabViewHistory = new CustomBottomTabContentView(this, "履歴", R.drawable.selector_bottom_tab_history);
        tabHistory = tabHost.newTabSpec("履歴");
        tabHistory.setIndicator(tabViewHistory);
        Bundle bundle4 = new Bundle();
        bundle4.putString("name", "履歴");
        tabHost.addTab(tabHistory, TabContainerFragment.class, bundle4);

        View tabViewOthers = new CustomBottomTabContentView(this, "6Pack", R.drawable.selector_bottom_tab_others);
        tabOthers = tabHost.newTabSpec("6Pack");
        tabOthers.setIndicator(tabViewOthers);
        Bundle bundle5 = new Bundle();
        bundle5.putString("name", "6Pack");
        tabHost.addTab(tabOthers, TabContainerFragment.class, bundle5);

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
