package com.example.komuroshun.androidtabui.history

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView

import com.example.komuroshun.androidtabui.BaseFragment
import com.example.komuroshun.androidtabui.R
import kotlinx.android.synthetic.main.tab_track.*

/**
 * This Fragment set up a history scroll tab view and view pager.
 */
class HistoryTabFragment : BaseFragment() {
    private var mIndicatorOffset: Int = 0
    // タブのコンテナ
    private var mTrack: ViewGroup? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history_tab, container, false)
        val density = resources.displayMetrics.density
        mIndicatorOffset = (INDICATOR_OFFSET * density).toInt()

        // View を取得する
        mTrack = view.findViewById(R.id.track)

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

            // 現在の位置のタブの横幅
            val width = view.width
            // 現在の位置の次のタブの横幅
            val width2 = view2?.width ?: width

            // インディケータの幅
            val indicatorWidth = (width2 * positionOffset + width * (1 - positionOffset)).toInt()
            // インディケータの左端の位置
            val indicatorLeft = (left + positionOffset * width).toInt()
            // インディケータの幅と左端の位置をセット
            val layoutParams = indicator.layoutParams as FrameLayout.LayoutParams
            layoutParams.width = indicatorWidth
            layoutParams.setMargins(indicatorLeft, 0, 0, 0)
            indicator.layoutParams = layoutParams

            // タブ部分のスクローラー
            // インディケータが画面に入るように、タブの領域をスクロール
            track_scroller.scrollTo(indicatorLeft - mIndicatorOffset, 0)
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
        // インディケータのオフセット
        private val INDICATOR_OFFSET = 130 // 48dp
        /**
         * @return A new instance of fragment CardListFragment.
         */
        fun newInstance(): HistoryTabFragment {
            return HistoryTabFragment()
        }
    }
}
