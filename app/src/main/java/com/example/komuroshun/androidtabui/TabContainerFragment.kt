package com.example.komuroshun.androidtabui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.komuroshun.androidtabui.card.CardListFragment
import com.example.komuroshun.androidtabui.history.HistoryTabFragment
import com.example.komuroshun.androidtabui.sixpack.SixPackFragment

/**
 * BaseFragment.onFragmentInteractionListner を拡張した Fragment の切り替え操作をする
 */
class TabContainerFragment : Fragment(), BaseFragment.OnFragmentInteractionListener, CardListFragment.OnFragmentInteractionListener {

    internal val HOME_TAB = "ホーム"
    internal val HISTORY_TAB = "履歴"
    internal val SIXPACK_TAB = "6Pack"

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (savedInstanceState == null) {
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val tabName = arguments!!.getString("name")
            when (tabName) {
                HOME_TAB -> {
                    val cardListFragment = CardListFragment.newInstance()
                    fragmentTransaction.replace(R.id.TabContainer, cardListFragment, "0")
                }
                HISTORY_TAB -> {
                    val historyTabFragment = HistoryTabFragment.newInstance("Param1")
                    fragmentTransaction.replace(R.id.TabContainer, historyTabFragment, "0")
                }
                SIXPACK_TAB -> {
                    val sixPackFragment = SixPackFragment.newInstance("Param1", "Param2")
                    fragmentTransaction.replace(R.id.TabContainer, sixPackFragment, "0")
                }
            }
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

    /**
     *
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    /**
     * @param uri Uri
     * @author Shun Komuro
     */
    override fun onFragmentInteraction(uri: Uri) {}
}// Required empty public constructor
