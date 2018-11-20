package com.example.komuroshun.androidtabui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.komuroshun.androidtabui.card.CardListFragment
import com.example.komuroshun.androidtabui.history.HistoryTabFragment
import com.example.komuroshun.androidtabui.qiita.QiitaArticlesFragment
import com.example.komuroshun.androidtabui.weather.CityListFragment

/**
 * BaseFragment.onFragmentInteractionListner を拡張した Fragment の切り替え操作をする
 */
class TabContainerFragment : Fragment(),
        BaseFragment.OnFragmentInteractionListener,
        CardListFragment.OnFragmentInteractionListener {

    companion object {
        const val ARG_TAB_ID = "tabId"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (savedInstanceState == null) {
            val fragmentTransaction = childFragmentManager.beginTransaction()
            val tabId = arguments!!.getInt(ARG_TAB_ID)
            var targetFragment: Fragment? = null
            when (tabId) {
                R.id.homeTabContainer -> { targetFragment = CardListFragment.newInstance() }
                R.id.noticeTabContainer -> { targetFragment = CityListFragment.newInstance() }
                R.id.historyTabContainer -> { targetFragment = HistoryTabFragment.newInstance() }
                R.id.othersTabContainer -> { targetFragment = QiitaArticlesFragment.newInstance() }
            }

            if (targetFragment != null) {
                fragmentTransaction.replace(R.id.TabContainer, targetFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
        return inflater.inflate(R.layout.fragment_tab_container, container, false)
    }

    /**
     * @param uri Uri
     * @author Shun Komuro
     */
    override fun onFragmentInteraction(uri: Uri) {
        var anonymousFragment = Class.forName(uri.fragment).newInstance() as BaseFragment
        val fragmentTransaction = childFragmentManager.beginTransaction()
        var args = Bundle()
        var queryParameterNames: Set<String> = uri.queryParameterNames
        for (queryParameterName in queryParameterNames) {
            args.putString("arg_${queryParameterName}", uri.getQueryParameter(queryParameterName))
        }
        anonymousFragment.setArguments(args)
        // TODO replace だと画面に表示されない
        if (Class.forName(uri.fragment).name == "com.example.komuroshun.androidtabui.WebViewFragment") {
            fragmentTransaction.add(R.id.TabContainer, anonymousFragment)
        } else {
            fragmentTransaction.replace(R.id.TabContainer, anonymousFragment)
        }
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onFragmentBack() {
        childFragmentManager.popBackStack()
    }
}
