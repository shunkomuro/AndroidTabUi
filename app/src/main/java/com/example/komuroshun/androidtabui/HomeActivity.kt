package com.example.komuroshun.androidtabui

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentTabHost
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TabHost
import android.widget.TabWidget

/**
 * Activities manage fragments.
 * @author Shun Komuro
 * @version 1.0
 */
class HomeActivity : AppCompatActivity(), /*FragmentTabHost.OnTabChangeListener,*/ PageFragment.OnFragmentInteractionListener, TabContainerFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Toolbar Settings
        val toolbar = findViewById<View>(R.id.tool_bar) as Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        val spinner = toolbar.findViewById<View>(R.id.tool_bar_spinner) as Spinner
        spinner.adapter = ArrayAdapter.createFromResource(this, R.array.fragment_names,
                android.R.layout.simple_spinner_dropdown_item)

        val tabHost = findViewById<FragmentTabHost>(android.R.id.tabhost)
        tabHost.setup(this, supportFragmentManager, R.id.container)

        // 画面下部タブメニュー
        val tabHome: TabHost.TabSpec
        val tabNotice: TabHost.TabSpec
        val tabPoint: TabHost.TabSpec
        val tabHistory: TabHost.TabSpec
        val tabOthers: TabHost.TabSpec
        // タブ生成1
        val tabViewHome = CustomBottomTabContentView(this, "ホーム", R.drawable.selector_bottom_tab_home)
        tabHome = tabHost.newTabSpec("ホーム")
        tabHome.setIndicator(tabViewHome)
        // クラス調査使い方
        val bundle1 = Bundle()
        bundle1.putString("name", "ホーム")
        tabHost.addTab(tabHome, TabContainerFragment::class.java, bundle1)
        // タブ生成2
        val tabViewNotice = CustomBottomTabContentView(this, "お知らせ", R.drawable.selector_bottom_tab_notice)
        tabNotice = tabHost.newTabSpec("お知らせ")
        tabNotice.setIndicator(tabViewNotice)
        val bundle2 = Bundle()
        bundle2.putString("name", "お知らせ")
        tabHost.addTab(tabNotice, PageFragment::class.java, bundle2)
        // タブ生成3
        val tabViewPoint = CustomBottomTabContentView(this, "ポイント", R.drawable.selector_bottom_tab_point)
        tabPoint = tabHost.newTabSpec("ポイント")
        tabPoint.setIndicator(tabViewPoint)
        val bundle3 = Bundle()
        bundle3.putString("name", "ポイント")
        tabHost.addTab(tabPoint, PageFragment::class.java, bundle3)

        val tabViewHistory = CustomBottomTabContentView(this, "履歴", R.drawable.selector_bottom_tab_history)
        tabHistory = tabHost.newTabSpec("履歴")
        tabHistory.setIndicator(tabViewHistory)
        val bundle4 = Bundle()
        bundle4.putString("name", "履歴")
        tabHost.addTab(tabHistory, TabContainerFragment::class.java, bundle4)

        val tabViewOthers = CustomBottomTabContentView(this, "6Pack", R.drawable.selector_bottom_tab_others)
        tabOthers = tabHost.newTabSpec("6Pack")
        tabOthers.setIndicator(tabViewOthers)
        val bundle5 = Bundle()
        bundle5.putString("name", "6Pack")
        tabHost.addTab(tabOthers, TabContainerFragment::class.java, bundle5)

        // リスナー登録
//        tabHost.setOnTabChangedListener(this)
    }

//    override fun onTabChanged(tabId: String) {}

    /**
     * Switch Fragments only.
     * @param uri Uri
     * @author Shun Komuro
     */
    override fun onFragmentInteraction(uri: Uri) {}

    companion object {

        private val TAG = HomeActivity::class.java.name
    }
}
