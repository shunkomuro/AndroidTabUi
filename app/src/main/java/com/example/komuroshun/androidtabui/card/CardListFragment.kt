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
 * This fragment manage CardListViewModel.
 */
class CardListFragment : ListFragment() {

    //TODO: Inject context and change to val
    private var mCardListViewModel: CardListViewModel? = null
    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_card_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mCardListViewModel = CardListViewModel.create(activity!!)

        listAdapter = mCardListViewModel!!.getCardListAdapter()

        // 8dp
        val padding = (resources.displayMetrics.density * 8).toInt()
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
         * @return A new instance of fragment CardListFragment.
         */
        fun newInstance(): CardListFragment {
            return CardListFragment()
        }
    }
}// Required empty public constructor
