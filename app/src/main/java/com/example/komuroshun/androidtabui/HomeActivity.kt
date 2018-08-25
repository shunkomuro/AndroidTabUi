package com.example.komuroshun.androidtabui

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Activities manage fragments.
 * @author Shun Komuro
 * @version 1.0
 */
class HomeActivity : AppCompatActivity(), /*FragmentTabHost.OnTabChangeListener,*/ PageFragment.OnFragmentInteractionListener, TabContainerFragment.OnFragmentInteractionListener {
    private var homeIsAlreadyDisplayed = false
    private var noticeIsAlreadyDisplayed = false
    private var pointIsAlreadyDisplayed = false
    private var historyIsAlreadyDisplayed = false
    private var othersIsAlreadyDisplayed = false
    private var currentTabContainer: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        currentTabContainer = homeTabContainer
        var firstViewFragment = TabContainerFragment()
        var bundle = Bundle()
        bundle.putString("name", "ホーム")
        firstViewFragment!!.setArguments(bundle)
        switchToSelectedTab(selectedTabFragment = firstViewFragment, selectedTabId = R.id.homeTabContainer, selectedTab = homeTabContainer)
        homeIsAlreadyDisplayed = true

        // Toolbar Settings
        val toolbar = findViewById<View>(R.id.tool_bar) as Toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        val spinner = toolbar.findViewById<View>(R.id.tool_bar_spinner) as Spinner
        spinner.adapter = ArrayAdapter.createFromResource(this, R.array.fragment_names,
                android.R.layout.simple_spinner_dropdown_item)

        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val bundle = Bundle()
            var selectedTabFragment: Fragment? = null
            var selectedTab: View? = null
            var selectedTabId: Int = 0
            when (item.itemId) {
                R.id.nav_home -> {
                    if (!homeIsAlreadyDisplayed) {
                        selectedTabFragment = TabContainerFragment()
                        bundle.putString("name", "ホーム")
                        selectedTabFragment!!.setArguments(bundle)
                        selectedTabId = R.id.homeTabContainer
                        homeIsAlreadyDisplayed = true
                    }
                    selectedTab = homeTabContainer
                }
                R.id.nav_notice -> {
                    if (!noticeIsAlreadyDisplayed) {
                        selectedTabFragment = PageFragment()
                        bundle.putString("name", "お知らせ")
                        selectedTabFragment!!.setArguments(bundle)
                        selectedTabId = R.id.noticeTabContainer
                        noticeIsAlreadyDisplayed = true
                    }
                    selectedTab = noticeTabContainer
                }
                R.id.nav_point -> {
                    if (!pointIsAlreadyDisplayed) {
                        selectedTabFragment = PageFragment()
                        bundle.putString("name", "ポイント")
                        selectedTabFragment!!.setArguments(bundle)
                        selectedTabId = R.id.pointTabContainer
                        pointIsAlreadyDisplayed = true
                    }
                    selectedTab = pointTabContainer
                }
                R.id.nav_history -> {
                    if (!historyIsAlreadyDisplayed) {
                        selectedTabFragment = TabContainerFragment()
                        bundle.putString("name", "履歴")
                        selectedTabFragment!!.setArguments(bundle)
                        selectedTabId = R.id.historyTabContainer
                        historyIsAlreadyDisplayed = true
                    }
                    selectedTab = historyTabContainer
                }
                R.id.nav_others -> {
                    if (!othersIsAlreadyDisplayed) {
                        selectedTabFragment = TabContainerFragment()
                        bundle.putString("name", "6Pack")
                        selectedTabFragment!!.setArguments(bundle)
                        selectedTabId = R.id.othersTabContainer
                        othersIsAlreadyDisplayed = true
                    }
                    selectedTab = othersTabContainer
                }
            }
            switchToSelectedTab(selectedTabFragment = selectedTabFragment, selectedTabId = selectedTabId, selectedTab = selectedTab)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun switchToSelectedTab(selectedTabFragment: Fragment? = null, selectedTabId: Int, selectedTab: View? = null) {
        if (selectedTabFragment != null) {
            supportFragmentManager.beginTransaction()
                    .replace(selectedTabId, selectedTabFragment)
                    .commit()
        }

        if (currentTabContainer != selectedTab) {
            selectedTab!!.setVisibility(View.VISIBLE)
            currentTabContainer!!.setVisibility(View.GONE)
            currentTabContainer = selectedTab
        }
    }
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
