package com.example.komuroshun.androidtabui

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.widget_toolbar.*

/**
 * Activities manage fragments.
 * @author Shun Komuro
 * @version 1.0
 */
class HomeActivity : AppCompatActivity(), PageFragment.OnFragmentInteractionListener {

    companion object {
        const private val KEY_CURRENT_MENU_ID = "key_current_menu_id"
    }

    private var currentMenuId = 0
    private var currentTabId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar.inflateMenu(R.menu.menu_toolbar)

        if (savedInstanceState == null) {
            currentMenuId = R.id.nav_home
        } else {
            currentMenuId = savedInstanceState.getInt(KEY_CURRENT_MENU_ID)
        }
        switchToSelectedTab(currentMenuId)

        BottomNavigationViewHelper.disableShiftMode(navigationBottom);
        navigationBottom.setOnNavigationItemSelectedListener { item ->
            switchToSelectedTab(item.itemId)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(KEY_CURRENT_MENU_ID, currentMenuId)
    }

    private fun switchToSelectedTab(selectedMenuId: Int) {
        var isCreated = false
        var selectedTabId = 0
        when (selectedMenuId) {
            R.id.nav_home -> {
                if (homeTabContainer.findViewById<FrameLayout>(R.id.TabContainer) != null) {
                    isCreated = true
                }
                selectedTabId = R.id.homeTabContainer
            }
            R.id.nav_notice -> {
                if (noticeTabContainer.findViewById<FrameLayout>(R.id.TabContainer) != null) {
                    isCreated = true
                }
                selectedTabId = R.id.noticeTabContainer
            }
            R.id.nav_point -> {
                if (pointTabContainer.findViewById<FrameLayout>(R.id.TabContainer) != null) {
                    isCreated = true
                }
                selectedTabId = R.id.pointTabContainer
            }
            R.id.nav_history -> {
                if (historyTabContainer.findViewById<FrameLayout>(R.id.TabContainer) != null) {
                    isCreated = true
                }
                selectedTabId = R.id.historyTabContainer
            }
            R.id.nav_others -> {
                if (othersTabContainer.findViewById<FrameLayout>(R.id.TabContainer) != null) {
                    isCreated = true
                }
                selectedTabId = R.id.othersTabContainer
            }
        }

        if (currentTabId == selectedTabId) {

        } else {
            if (isCreated == false) {
                val selectedTabFragment = TabContainerFragment()
                val bundle = Bundle()
                bundle.putInt(TabContainerFragment.ARG_TAB_ID, selectedTabId)
                selectedTabFragment.setArguments(bundle)

                supportFragmentManager.beginTransaction()
                        .replace(selectedTabId, selectedTabFragment)
                        .commit()
            }

            findViewById<FrameLayout>(selectedTabId).setVisibility(View.VISIBLE)
            var currentTab = findViewById<FrameLayout>(currentTabId)
            if (currentTab != null) { currentTab.setVisibility(View.GONE) }

            currentTabId = selectedTabId
            currentMenuId = selectedMenuId
        }
    }

    /**
     * Switch Fragments only.
     * @param uri Uri
     * @author Shun Komuro
     */
    override fun onFragmentInteraction(uri: Uri) {}
}
