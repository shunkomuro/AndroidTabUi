package com.example.komuroshun.androidtabui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 *
 */
open class BaseFragment : Fragment() {

    protected var mListener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = parentFragment as BaseFragment.OnFragmentInteractionListener?
        if (mListener == null) {
            throw RuntimeException(context!!.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     *
     */
    interface OnFragmentInteractionListener {
        // TODO: 画面遷移のやり方を整理する
        fun onFragmentInteraction(cityName: String?, cityId: String?)
        fun onFragmentInteraction(url: String?)
        fun onFragmentInteraction(uri: Uri)
        fun onFragmentBack()
    }
}
