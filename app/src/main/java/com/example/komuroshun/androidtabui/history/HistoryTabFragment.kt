package com.example.komuroshun.androidtabui.history

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.TextView

import com.example.komuroshun.androidtabui.BaseFragment
import com.example.komuroshun.androidtabui.R
import com.example.komuroshun.androidtabui.util.CustomLog

/**
 * This Fragment set up a history scroll tab view and view pager.
 * @author Shun Komuro
 * @version 1.0
 */
//    private OnFragmentInteractionListener mListener;

class HistoryTabFragment : BaseFragment() {
    private var mIndicatorOffset: Int = 0

    // タブ部分のスクローラー
    private var mTrackScroller: HorizontalScrollView? = null
    // タブのコンテナ
    private var mTrack: ViewGroup? = null
    // インディケータ
    private var mIndicator: View? = null

    private val mParam1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        if (getArguments() != null) {
        //            mParam1 = getArguments().getString(ARG_PARAM1);
        //        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history_tab, container, false)
        val density = resources.displayMetrics.density
        mIndicatorOffset = (INDICATOR_OFFSET * density).toInt()

        //View を取得
        mTrackScroller = view.findViewById(R.id.track_scroller)
        mTrack = view.findViewById(R.id.track)
        mIndicator = view.findViewById(R.id.indicator)

        // ViewPager のセットアップ
        val adapter = ViewPagerAdapter(childFragmentManager)
        val pager = view.findViewById<ViewPager>(R.id.historyviewpager)
        pager.adapter = adapter
        pager.addOnPageChangeListener(PageChangeListener())

        // タブをコンテナに追加
        val layoutInfater = LayoutInflater.from(activity)
        for (i in 0 until adapter.count) {
            val tv = layoutInfater.inflate(R.layout.tab_item, mTrack, false) as TextView
            tv.text = adapter.getPageTitle(i)
            tv.setOnClickListener { pager.currentItem = i }
            mTrack!!.addView(tv)
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    private inner class PageChangeListener : ViewPager.OnPageChangeListener {
        private var mScrollingState = ViewPager.SCROLL_STATE_IDLE

        override fun onPageSelected(position: Int) {
            // スクロール中はonPageScrolled()で描画するのでここではしない
            if (mScrollingState == ViewPager.SCROLL_STATE_IDLE) {
                updateIndicatorPosition(position, 0f)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            mScrollingState = state
        }

        override fun onPageScrolled(position: Int, positionOffset: Float,
                                    positionOffsetPixels: Int) {
            updateIndicatorPosition(position, positionOffset)
        }

        private fun updateIndicatorPosition(position: Int, positionOffset: Float) {
            // 現在の位置のタブのView
            val view = mTrack!!.getChildAt(position)
            // 現在の位置の次のタブのView、現在の位置が最後のタブのときはnull
            val view2 = if (position == mTrack!!.childCount - 1)
                null
            else
                mTrack!!.getChildAt(position + 1)

            val left = view.left
//            CustomLog.output("debug", "updateIndicatorPosition", "left : " + left.toString())

            // 現在の位置のタブの横幅
            val width = view.width
//            CustomLog.output("debug", "updateIndicatorPosition", "width : " + width.toString())
            // 現在の位置の次のタブの横幅
            val width2 = view2?.width ?: width
//            CustomLog.output("debug", "updateIndicatorPosition", "width2 : " + width2.toString())

            // インディケータの幅
            val indicatorWidth = (width2 * positionOffset + width * (1 - positionOffset)).toInt()
//            CustomLog.output("debug", "updateIndicatorPosition", "indicatorWidth : " + indicatorWidth.toString())
            // インディケータの左端の位置
            val indicatorLeft = (left + positionOffset * width).toInt()
//            CustomLog.output("debug", "updateIndicatorPosition", "indicatorLeft : " + indicatorLeft.toString())
//            CustomLog.output("debug", "updateIndicatorPosition", "mIndicatorOffset : " + mIndicatorOffset.toString())
            // インディケータの幅と左端の位置をセット
            val layoutParams = mIndicator!!
                    .layoutParams as FrameLayout.LayoutParams
            layoutParams.width = indicatorWidth
            layoutParams.setMargins(indicatorLeft, 0, 0, 0)
            mIndicator!!.layoutParams = layoutParams

            // インディケータが画面に入るように、タブの領域をスクロール
            mTrackScroller!!.scrollTo(indicatorLeft - mIndicatorOffset, 0)
        }
    }

    private class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return SimpleFragment.getInstance(position)
        }

        override fun getCount(): Int {
            return sTabs.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return sTabs[position]
        }

        class SimpleFragment : Fragment() {

            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                      savedInstanceState: Bundle?): View? {
                return inflater.inflate(R.layout.fragment_page2, container, false)
            }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)
                val position = arguments!!.getInt("position")
                val color = Color.HSVToColor(floatArrayOf((30 * position).toFloat(), 0.1f, 0.9f))
                view.setBackgroundColor(color)
                val tv = view.findViewById<View>(R.id.textView1) as TextView
                tv.text = "Here is page $position"
            }

            companion object {

                fun getInstance(position: Int): SimpleFragment {
                    val f = SimpleFragment()
                    val args = Bundle()
                    args.putInt("position", position)
                    f.arguments = args
                    return f
                }
            }
        }

        companion object {
            // タブの項目
            private val sTabs = arrayOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto")
        }
    }

    companion object {

        //    private static final String ARG_PARAM1 = "param1";
        // インディケータのオフセット
        private val INDICATOR_OFFSET = 130 // 48dp

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment HistoryTabFragment.
         */
        fun newInstance(param1: String): HistoryTabFragment {
//        Bundle args = new Bundle();
            //        args.putString(ARG_PARAM1, param1);
            //        fragment.setArguments(args);
            return HistoryTabFragment()
        }
    }
}// Required empty public constructor
