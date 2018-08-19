package com.example.komuroshun.androidtabui

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SampleFragmentPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    internal val PAGE_COUNT = 2
    private val tabTitles = arrayOf("Tab1", "Tab2")

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment? {
        // fragmentを切り替える
        when (position) {
            0 -> return TabPageFragment()
            1 -> return TabPageFragment()
        }
        return null
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return tabTitles[position]
    }
}
