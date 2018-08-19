package com.example.komuroshun.androidtabui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChildPageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChildPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChildPageFragment : Fragment() {

    private var mGenerateCount: String? = null

    private var mListener: ChildPageFragment.OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        mListener = parentFragment as ChildPageFragment.OnFragmentInteractionListener?
        if (arguments != null) {
            mGenerateCount = arguments!!.getString(GENERATE_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        // Inflate the layout for this fragment
        container!!.removeAllViews()
        val view = inflater.inflate(R.layout.fragment_child_page, container, false)

        val generateCountTextView = view.findViewById<TextView>(R.id.generateCountOutput)
        generateCountTextView.text = mGenerateCount

        val btClick = view.findViewById<Button>(R.id.fragmentGenerateButton) // （7）
        val listener = GenarateFragmentListener() // （8）
        btClick.setOnClickListener(listener)

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action != KeyEvent.ACTION_DOWN) return@OnKeyListener false
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                Log.d(TAG, "onKey")
                mListener!!.onFragmentBack()
                return@OnKeyListener true
            }
            false
        })

        return view
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
        fun onFragmentBack()
    }

    private inner class GenarateFragmentListener : View.OnClickListener {
        override fun onClick(view: View) {
            // 親フラグメントに生成依頼
            mListener!!.onFragmentInteraction()
        }
    }

    companion object {

        private val TAG = ChildPageFragment::class.java.name

        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val GENERATE_COUNT = "generateCount"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param generateCount generate count.
         * @return A new instance of fragment ChildPageFragment.
         */
        fun newInstance(generateCount: String): ChildPageFragment {
            val fragment = ChildPageFragment()
            val args = Bundle()
            args.putString(GENERATE_COUNT, generateCount)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
