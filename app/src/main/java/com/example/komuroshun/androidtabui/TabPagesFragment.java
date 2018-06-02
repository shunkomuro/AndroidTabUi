package com.example.komuroshun.androidtabui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * メモ：
 * 子は親を知らない状態。(TabPagesFragment が子, PageFragment が親)
 * 子は自分を生成してもらうためのリスナーの用意だけしておく。
 * 表示内容は onCreateView() の段階で全て自分で用意する。情報は関連の Store クラスから取得する。
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabPagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabPagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabPagesFragment extends Fragment implements TabPageFragment.OnFragmentInteractionListener {

    private static final String TAG = TabPagesFragment.class.getName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PARENT_TAB_NAME = "parentTabName";

    private String mParentTabName;
    private OnFragmentInteractionListener mParentListener;
    private View mView;
    private ViewPager mPager;

    public TabPagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param parentTabName A tab name of parent Activity.
     * @return A new instance of fragment TabPagesFragment.
     */
    public static TabPagesFragment newInstance(String parentTabName) {
        TabPagesFragment fragment = new TabPagesFragment();
        Bundle args = new Bundle();
        args.putString(PARENT_TAB_NAME, parentTabName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParentTabName = getArguments().getString(PARENT_TAB_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, mParentTabName + " : onCreateView");
        mView = inflater.inflate(R.layout.fragment_tab_pages, container, false);
        // タブにページャをセット
        mPager = (ViewPager)mView.findViewById(R.id.viewpager);
        mPager.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager(), getContext()));
        TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);

        return mView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mParentListener = null;
    }

    @Override
    public void onFragmentInteraction() {
        // 親フラグメントに生成依頼
        Log.d(TAG, "onFragmentInteraction");
        Fragment parentFragment = getParentFragment();
        mParentListener = (TabPagesFragment.OnFragmentInteractionListener) parentFragment;
        mParentListener.onFragmentInteraction();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
