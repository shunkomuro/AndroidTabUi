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
import com.example.komuroshun.androidtabui.sixpack.SixPackFragment
import com.example.komuroshun.androidtabui.weather.CityListFragment
import com.example.komuroshun.androidtabui.weather.WeatherInfoFragment

private const val ARG_TAB_NAME = "tabName"

/**
 * BaseFragment.onFragmentInteractionListner を拡張した Fragment の切り替え操作をする
 */
class TabContainerFragment : Fragment(), BaseFragment.OnFragmentInteractionListener, CardListFragment.OnFragmentInteractionListener {

    internal val HOME_TAB = "ホーム"
    internal val WEATHER_TAB = "天気"
    internal val HISTORY_TAB = "履歴"
    internal val SIXPACK_TAB = "6Pack"

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (savedInstanceState == null) {
            val fragmentTransaction = childFragmentManager.beginTransaction()
            val tabName = arguments!!.getString(ARG_TAB_NAME)
            var targetFragment: Fragment? = null
            when (tabName) {
                HOME_TAB -> {
                    targetFragment = CardListFragment.newInstance()
                }
                WEATHER_TAB -> {
                    targetFragment = CityListFragment.newInstance("tes1", "tes2")
                }
                HISTORY_TAB -> {
                    targetFragment = HistoryTabFragment.newInstance("Param1")
                }
                SIXPACK_TAB -> {
                    targetFragment = SixPackFragment.newInstance("Param1", "Param2")
                }
            }
            fragmentTransaction.replace(R.id.TabContainer, targetFragment)
            fragmentTransaction.addToBackStack(null) // 戻るボタンでreplace前に戻る
            fragmentTransaction.commit()
        }
        return inflater.inflate(R.layout.fragment_tab_container, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    /**
     * @param uri Uri
     * @author Shun Komuro
     */
    override fun onFragmentInteraction(cityName: String?, cityId: String?) {
        //TODO routing liburary を探す
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val weatherInfoFragment = WeatherInfoFragment.newInstance(cityName!!, cityId!!)
        fragmentTransaction.replace(R.id.TabContainer, weatherInfoFragment)
        fragmentTransaction.addToBackStack(null) // 戻るボタンでreplace前に戻る
        fragmentTransaction.commit()
    }

    /**
     * @param uri Uri
     * @author Shun Komuro
     */
    override fun onFragmentInteraction(uri: Uri) {

    }
}// Required empty public constructor
