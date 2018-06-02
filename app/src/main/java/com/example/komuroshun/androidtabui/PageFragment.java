package com.example.komuroshun.androidtabui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PageFragment extends Fragment implements
        TabPagesFragment.OnFragmentInteractionListener,
        ChildPageFragment.OnFragmentInteractionListener {

    private static final String TAG = PageFragment.class.getName();

    private String mParentTabName;
    private OnFragmentInteractionListener mParentListener;
    private View mView;
    private FragmentManager mFragmentManager;
    private int generateFragmentNumber;
    private boolean mInitialActivation = true;

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParentTabName = getArguments().getString("name");
        Log.d(TAG, mParentTabName + " : onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, mParentTabName + " : onCreateView");
        mView = inflater.inflate(R.layout.fragment_page, container, false);
        if (mInitialActivation) {
            mInitialActivation = false;
            generateFragmentNumber = 0;
            mFragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            generateFragmentNumber++;
            Fragment tabPagesFragment = TabPagesFragment.newInstance(mParentTabName);
            fragmentTransaction.replace(R.id.childPageContainer, tabPagesFragment,
                    String.valueOf(generateFragmentNumber));
            fragmentTransaction.addToBackStack(null); // 戻るボタンでreplace前に戻る
            fragmentTransaction.commit();
        }

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mParentListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mParentListener = null;
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
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onFragmentInteraction() {
        //生成した後いくつめのフラグメントかをChildPageFragment に表示
        Log.d(TAG, "onFragmentInteraction");
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        generateFragmentNumber++;
        Fragment childPageFragment =
                ChildPageFragment.newInstance(String.valueOf(generateFragmentNumber));
        fragmentTransaction.replace(R.id.childPageContainer,childPageFragment,
                String.valueOf(generateFragmentNumber));
        fragmentTransaction.addToBackStack(null); // 戻るボタンでreplace前に戻る
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentBack () {
        mFragmentManager.popBackStack();
        generateFragmentNumber--;
    }
}
