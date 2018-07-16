package com.example.komuroshun.androidtabui.history;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.komuroshun.androidtabui.BaseFragment;
import com.example.komuroshun.androidtabui.R;
import com.example.komuroshun.androidtabui.util.CustomLog;

/**
 * This Fragment set up a history scroll tab view and view pager.
 * @author Shun Komuro
 * @version 1.0
 */
public class HistoryTabFragment extends BaseFragment {

//    private static final String ARG_PARAM1 = "param1";
    // インディケータのオフセット
    private static final int INDICATOR_OFFSET = 130; // 48dp
    private int mIndicatorOffset;

    // タブ部分のスクローラー
    private HorizontalScrollView mTrackScroller;
    // タブのコンテナ
    private ViewGroup mTrack;
    // インディケータ
    private View mIndicator;

    private String mParam1;

//    private OnFragmentInteractionListener mListener;

    public HistoryTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HistoryTabFragment.
     */
    public static HistoryTabFragment newInstance(String param1) {
        HistoryTabFragment fragment = new HistoryTabFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_tab, container, false);
        final float density = getResources().getDisplayMetrics().density;
        mIndicatorOffset = (int) (INDICATOR_OFFSET * density);

        //View を取得
        mTrackScroller = view.findViewById(R.id.track_scroller);
        mTrack = view.findViewById(R.id.track);
        mIndicator = view.findViewById(R.id.indicator);

        // ViewPager のセットアップ
        PagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        final ViewPager pager = view.findViewById(R.id.historyviewpager);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new PageChangeListener());

        // タブをコンテナに追加
        LayoutInflater layoutInfater = LayoutInflater.from(getActivity());
        for (int i = 0; i < adapter.getCount(); i++) {
            final int position = i;
            TextView tv = (TextView) layoutInfater.inflate(R.layout.tab_item, mTrack, false);
            tv.setText(adapter.getPageTitle(position));
            tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(position);
                }
            });
            mTrack.addView(tv);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        private int mScrollingState = ViewPager.SCROLL_STATE_IDLE;

        @Override
        public void onPageSelected(int position) {
            // スクロール中はonPageScrolled()で描画するのでここではしない
            if (mScrollingState == ViewPager.SCROLL_STATE_IDLE) {
                updateIndicatorPosition(position, 0);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollingState = state;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            updateIndicatorPosition(position, positionOffset);
        }

        private void updateIndicatorPosition(int position, float positionOffset) {
            // 現在の位置のタブのView
            final View view = mTrack.getChildAt(position);
            // 現在の位置の次のタブのView、現在の位置が最後のタブのときはnull
            final View view2 = position == (mTrack.getChildCount() - 1) ? null
                    : mTrack.getChildAt(position + 1);

            int left = view.getLeft();
            CustomLog.output("debug", "updateIndicatorPosition", "left : " + String.valueOf(left));

            // 現在の位置のタブの横幅
            int width = view.getWidth();
            CustomLog.output("debug", "updateIndicatorPosition", "width : " + String.valueOf(width));
            // 現在の位置の次のタブの横幅
            int width2 = view2 == null ? width : view2.getWidth();
            CustomLog.output("debug", "updateIndicatorPosition", "width2 : " + String.valueOf(width2));

            // インディケータの幅
            int indicatorWidth = (int) (width2 * positionOffset + width
                    * (1 - positionOffset));
            CustomLog.output("debug", "updateIndicatorPosition", "indicatorWidth : " + String.valueOf(indicatorWidth));
            // インディケータの左端の位置
            int indicatorLeft = (int) (left + positionOffset * width);
            CustomLog.output("debug", "updateIndicatorPosition", "indicatorLeft : " + String.valueOf(indicatorLeft));
            CustomLog.output("debug", "updateIndicatorPosition", "mIndicatorOffset : " + String.valueOf(mIndicatorOffset));
            // インディケータの幅と左端の位置をセット
            final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mIndicator
                    .getLayoutParams();
            layoutParams.width = indicatorWidth;
            layoutParams.setMargins(indicatorLeft, 0, 0, 0);
            mIndicator.setLayoutParams(layoutParams);

            // インディケータが画面に入るように、タブの領域をスクロール
            mTrackScroller.scrollTo(indicatorLeft - mIndicatorOffset, 0);
        }
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        // タブの項目
        private static final String[] sTabs = { "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto" };

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return SimpleFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return sTabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return sTabs[position];
        }

        public static class SimpleFragment extends Fragment {

            public static SimpleFragment getInstance(int position) {
                SimpleFragment f = new SimpleFragment();
                Bundle args = new Bundle();
                args.putInt("position", position);
                f.setArguments(args);
                return f;
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                return inflater.inflate(R.layout.fragment_page2, container, false);
            }

            @Override
            public void onViewCreated(View view, Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);
                int position = getArguments().getInt("position");
                int color = Color.HSVToColor(new float[] { 30 * position, 0.1f, 0.9f });
                view.setBackgroundColor(color);
                TextView tv = (TextView) view.findViewById(R.id.textView1);
                tv.setText("Here is page " + position);
            }
        }
    }
}
