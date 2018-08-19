package com.example.komuroshun.androidtabui.card

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.AdapterView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ListView

import com.example.komuroshun.androidtabui.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CardListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CardListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardListFragment : ListFragment() {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //    private static final String ARG_PARAM1 = "param1";
    //    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //    private String mParam1;
    //    private String mParam2;

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            //            mParam1 = getArguments().getString(ARG_PARAM1);
            //            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_card_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // インストールされているアプリを取得
        val packageManager = activity!!.packageManager
        val packageInfoList = packageManager
                .getInstalledPackages(PackageManager.GET_ACTIVITIES)

        val adapter = CardListAdapter(activity!!)

        // アダプターにアプリ情報を追加
        if (packageInfoList != null) {
            for (info in packageInfoList) {
                adapter.add(info)
            }
        }
        listAdapter = adapter

        // 8dp
        val padding = (resources.displayMetrics.density * 8).toInt()
        val listView = listView
        listView.setPadding(padding, 0, padding, 0)
        listView.scrollBarStyle = ListView.SCROLLBARS_OUTSIDE_OVERLAY
        listView.divider = null

        // 余白用のヘッダー、フッターを追加
        val layoutInflater = LayoutInflater.from(activity)
        val header = layoutInflater.inflate(R.layout.parts_card_list_header, listView, false)
        //TODO:campaign button is visible or gone
        val footer = layoutInflater.inflate(R.layout.parts_card_list_footer, listView, false)
        listView.addHeaderView(header, null, false)
        listView.addFooterView(footer, null, false)
        listView.onItemClickListener = AdapterView.OnItemClickListener { arg0, card, arg2, arg3 -> startCardAnimation(card) }

        val campaignButton = activity!!.findViewById<Button>(R.id.btn_campaign)
        val feedinButton = AlphaAnimation(0f, 1f)
        feedinButton.duration = 100
        feedinButton.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                campaignButton.visibility = View.VISIBLE
                //                        listView.setVisibility(View.GONE);
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        campaignButton.startAnimation(feedinButton)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //        if (context instanceof OnFragmentInteractionListener) {
        //            mListener = (OnFragmentInteractionListener) context;
        //        } else {
        //            throw new RuntimeException(context.toString()
        //                    + " must implement OnFragmentInteractionListener");
        //        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun startCardAnimation(card: View) {
        val listView = listView
        val listViewLocation = IntArray(2)
        listView.getLocationOnScreen(listViewLocation)
        // get Card coordinate after moving
        val toCardY = listViewLocation[1] + listView.height / 2 - card.height / 2
        // get Card coordinate before moving
        val cardLocation = IntArray(2)
        card.getLocationOnScreen(cardLocation)
        // toCardY - fromCardY
        val movingAmount = toCardY - cardLocation[1]
        // Setting Animation
        val cardMovingAnimation = TranslateAnimation(Animation.ABSOLUTE, 0.toFloat(),
                Animation.ABSOLUTE, 0.toFloat(),
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, movingAmount.toFloat())
        cardMovingAnimation.duration = 400
        cardMovingAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                card.translationY = movingAmount.toFloat()
                flipCard(card)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        card.startAnimation(cardMovingAnimation)
    }

    private fun flipCard(rotationView: View) {
        val flipAnimation = FlipAnimation(rotationView, rotationView)

        if (rotationView.visibility == View.GONE) {
            flipAnimation.reverse()
        }
        rotationView.startAnimation(flipAnimation)
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
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * //     * @param param1 Parameter 1.
         * //     * @param param2 Parameter 2.
         * @return A new instance of fragment CardListFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(/*String param1, String param2*/): CardListFragment {
            val fragment = CardListFragment()
            val args = Bundle()
            //        args.putString(ARG_PARAM1, param1);
            //        args.putString(ARG_PARAM2, param2);
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
