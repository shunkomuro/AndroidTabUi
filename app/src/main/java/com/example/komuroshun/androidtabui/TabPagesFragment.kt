package com.example.komuroshun.androidtabui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * メモ：
 * 子は親を知らない状態。(TabPagesFragment が子, PageFragment が親)
 * 子は自分を生成してもらうためのリスナーの用意だけしておく。
 * 表示内容は onCreateView() の段階で全て自分で用意する。情報は関連の Store クラスから取得する。
 */

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TabPagesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TabPagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabPagesFragment : Fragment(), TabPageFragment.OnFragmentInteractionListener {

    private var mParentTabName: String? = null
    private var mParentListener: OnFragmentInteractionListener? = null
    private var mView: View? = null
    private var mPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParentTabName = arguments!!.getString(PARENT_TAB_NAME)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, mParentTabName!! + " : onCreateView")
        mView = inflater.inflate(R.layout.fragment_tab_pages, container, false)
        // タブにページャをセット
        mPager = mView!!.findViewById<View>(R.id.viewpager) as ViewPager
        mPager!!.adapter = SampleFragmentPagerAdapter(childFragmentManager, context!!)
        val tabLayout = mView!!.findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mPager)

        return mView
    }

    override fun onDetach() {
        super.onDetach()
        //        mParentListener = null;
    }

    override fun onFragmentInteraction() {
        // 親フラグメントに生成依頼
        Log.d(TAG, "onFragmentInteraction")
        val parentFragment = parentFragment
        mParentListener = parentFragment as TabPagesFragment.OnFragmentInteractionListener?
        mParentListener!!.onFragmentInteraction()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction()
    }

    companion object {

        private val TAG = TabPagesFragment::class.java.name

        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val PARENT_TAB_NAME = "parentTabName"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param parentTabName A tab name of parent Activity.
         * @return A new instance of fragment TabPagesFragment.
         */
        fun newInstance(parentTabName: String): TabPagesFragment {
            val fragment = TabPagesFragment()
            val args = Bundle()
            args.putString(PARENT_TAB_NAME, parentTabName)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
