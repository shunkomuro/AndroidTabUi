package com.example.komuroshun.androidtabui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
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
public class ChildPageFragment extends Fragment {

    private static final String TAG = ChildPageFragment.class.getName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String GENERATE_COUNT = "generateCount";

    private String mGenerateCount;

    private ChildPageFragment.OnFragmentInteractionListener mListener;

    public ChildPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param generateCount generate count.
     * @return A new instance of fragment ChildPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildPageFragment newInstance(String generateCount) {
        ChildPageFragment fragment = new ChildPageFragment();
        Bundle args = new Bundle();
        args.putString(GENERATE_COUNT, generateCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mListener = (ChildPageFragment.OnFragmentInteractionListener) getParentFragment();
        if (getArguments() != null) {
            mGenerateCount = getArguments().getString(GENERATE_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_child_page, container, false);

        TextView generateCountTextView = view.findViewById(R.id.generateCountOutput);
        generateCountTextView.setText(mGenerateCount);

        Button btClick = view.findViewById(R.id.fragmentGenerateButton); // （7）
        GenarateFragmentListener listener = new GenarateFragmentListener(); // （8）
        btClick.setOnClickListener(listener);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!= KeyEvent.ACTION_DOWN) return false;
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    Log.d(TAG, "onKey");
                    mListener.onFragmentBack();
                    return true;
                }
                return false;
            }

        });

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
        void onFragmentBack();
    }

    private class GenarateFragmentListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // 親フラグメントに生成依頼
            mListener.onFragmentInteraction();
        }
    }
}
