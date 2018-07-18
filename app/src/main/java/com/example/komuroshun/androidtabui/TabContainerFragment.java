package com.example.komuroshun.androidtabui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.komuroshun.androidtabui.history.HistoryTabFragment;
import com.example.komuroshun.androidtabui.sixpack.SixPackFragment;

/**
 * BaseFragment.onFragmentInteractionListner を拡張した Fragment の切り替え操作をする
 */
public class TabContainerFragment extends Fragment
        implements BaseFragment.OnFragmentInteractionListener{

    final String HISTORY_TAB = "履歴";
    final String SIXPACK_TAB = "6Pack";

    private OnFragmentInteractionListener mListener;

    public TabContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            String tabName = getArguments().getString("name");
            switch (tabName) {
                case HISTORY_TAB :
                    Fragment historyTabFragment = HistoryTabFragment.newInstance("Param1");
                    fragmentTransaction.replace(R.id.HistoryTabContainer, historyTabFragment, "0");
                    break;
                case SIXPACK_TAB :
                    Fragment sixPackFragment = SixPackFragment.newInstance("Param1", "Param2");
                    fragmentTransaction.replace(R.id.HistoryTabContainer, sixPackFragment, "0");
                    break;
            }
            fragmentTransaction.addToBackStack(null); // 戻るボタンでreplace前に戻る
            fragmentTransaction.commit();
        }
        return inflater.inflate(R.layout.fragment_tab_container, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     *
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * @param uri Uri
     * @author Shun Komuro
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
