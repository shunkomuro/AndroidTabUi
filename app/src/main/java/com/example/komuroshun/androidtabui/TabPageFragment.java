package com.example.komuroshun.androidtabui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChildPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChildPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabPageFragment extends Fragment {

    private static final String TAG = TabPageFragment.class.getName();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String GENERATE_COUNT = "generateCount";

    private String mGenerateCount;

    private TabPageFragment.OnFragmentInteractionListener mListener;

    public TabPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param generateCount generate count.
     * @return A new instance of fragment ChildPageFragment.
     */
    public static TabPageFragment newInstance(String generateCount) {
        TabPageFragment fragment = new TabPageFragment();
        Bundle args = new Bundle();
        args.putString(GENERATE_COUNT, generateCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            mGenerateCount = getArguments().getString(GENERATE_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
//        container.removeAllViews();
        mGenerateCount = "1";
        View view = inflater.inflate(R.layout.fragment_tab_page, container, false);

        TextView generateCountTextView = view.findViewById(R.id.generateCountOutput);
        generateCountTextView.setText(mGenerateCount);

        Button btClick = view.findViewById(R.id.fragmentGenerateButton); // （7）
        GenarateFragmentListener listener = new GenarateFragmentListener(); // （8）
        btClick.setOnClickListener(listener);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        mListener = null;
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
//        void onFragmentInteraction(Uri uri);
        void onFragmentInteraction();
    }

    private class GenarateFragmentListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // 親フラグメントに生成依頼
            Fragment parentFragment = getParentFragment();
            mListener = (TabPageFragment.OnFragmentInteractionListener) parentFragment;
            mListener.onFragmentInteraction();
        }
    }
}
