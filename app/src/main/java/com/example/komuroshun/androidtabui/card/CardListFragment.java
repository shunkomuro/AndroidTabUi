package com.example.komuroshun.androidtabui.card;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.komuroshun.androidtabui.BaseFragment;
import com.example.komuroshun.androidtabui.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardListFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CardListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment CardListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardListFragment newInstance(/*String param1, String param2*/) {
        CardListFragment fragment = new CardListFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // インストールされているアプリを取得
        PackageManager packageManager = getActivity().getPackageManager();
        List<PackageInfo> packageInfoList = packageManager
                .getInstalledPackages(PackageManager.GET_ACTIVITIES);

        CardListAdapter adapter = new CardListAdapter(getActivity());

        // アダプターにアプリ情報を追加
        if (packageInfoList != null) {
            for (PackageInfo info : packageInfoList) {
                adapter.add(info);
            }
        }
        setListAdapter(adapter);

        // 8dp
        int padding = (int) (getResources().getDisplayMetrics().density * 8);
        //TODO final
        final ListView listView = getListView();
        listView.setPadding(padding, 0, padding, 0);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setDivider(null);

        // 余白用のヘッダー、フッターを追加
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View header = layoutInflater.inflate(R.layout.parts_card_list_header_footer, listView, false);
        View footer = layoutInflater.inflate(R.layout.parts_card_list_header_footer, listView, false);
        listView.addHeaderView(header, null, false);
        listView.addFooterView(footer, null, false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, final View card, int arg2,
                                    long arg3) {
                Log.d("############","Items ");

                // get Card coordinate after moving
                // (ListView height / 2) - (Card height / 2)
                Log.d("TYOUSA","LISTVIEW_HEIGHT : " + String.valueOf(listView.getHeight())); // 高さ
                int[] listViewLocation = new int[2];
                listView.getLocationOnScreen(listViewLocation);
                Log.d("TYOUSA","LISTVIEW_Y : " + String.valueOf(listViewLocation[1])); // Y 座標

                Log.d("TYOUSA","CARD_HEIGHT : " + String.valueOf(card.getHeight())); // 高さ

                int[] cardLocation = new int[2];
                card.getLocationInWindow(cardLocation);
                Log.d("TYOUSA","FROM_CARD_Y : " + String.valueOf(cardLocation[1])); // Y 座標

                int toCardY = (listView.getHeight()/2)-(card.getHeight()/2);
                Log.d("TYOUSA","TO_CARD_Y : " + String.valueOf(toCardY)); // Y 座標
                final int movingAmount = toCardY - cardLocation[1];
                Animation cardMovingAnimation = new TranslateAnimation(Animation.ABSOLUTE, (float)0, Animation.ABSOLUTE, (float)0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, (float)movingAmount);
                cardMovingAnimation.setDuration(400);
                cardMovingAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        card.setTranslationY(movingAmount);
                        flipCard(card);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
                card.startAnimation(cardMovingAnimation);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void flipCard(View rotationView)
    {
        // わかりやすく変数に入れているだけ
        View rotationTargetLayout = rotationView;
        View cardFace = rotationView;
        View cardBack = rotationView;

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rotationTargetLayout.startAnimation(flipAnimation);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
