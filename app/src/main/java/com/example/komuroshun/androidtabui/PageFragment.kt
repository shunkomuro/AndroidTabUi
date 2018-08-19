package com.example.komuroshun.androidtabui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PageFragment : Fragment(), TabPagesFragment.OnFragmentInteractionListener, ChildPageFragment.OnFragmentInteractionListener {

    private var mParentTabName: String? = null
    private var mParentListener: OnFragmentInteractionListener? = null
    private var mView: View? = null
    private var mFragmentManager: FragmentManager? = null
    private var generateFragmentNumber: Int = 0
    private var mInitialActivation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mParentTabName = arguments!!.getString("name")
        Log.d(TAG, mParentTabName!! + " : onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, mParentTabName!! + " : onCreateView")
        mView = inflater.inflate(R.layout.fragment_page, container, false)
        if (mInitialActivation) {
            mInitialActivation = false
            generateFragmentNumber = 0
            mFragmentManager = childFragmentManager
            val fragmentTransaction = mFragmentManager!!.beginTransaction()
            generateFragmentNumber++
            val tabPagesFragment = TabPagesFragment.newInstance(mParentTabName!!)
            fragmentTransaction.replace(R.id.childPageContainer, tabPagesFragment,
                    generateFragmentNumber.toString())
            fragmentTransaction.addToBackStack(null) // 戻るボタンでreplace前に戻る
            fragmentTransaction.commit()
        }

        return mView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mParentListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mParentListener = null
    }

    /**
     *
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onFragmentInteraction() {
        //生成した後いくつめのフラグメントかをChildPageFragment に表示
        Log.d(TAG, "onFragmentInteraction")
        val fragmentTransaction = mFragmentManager!!.beginTransaction()
        generateFragmentNumber++
        val childPageFragment = ChildPageFragment.newInstance(generateFragmentNumber.toString())
        fragmentTransaction.replace(R.id.childPageContainer, childPageFragment,
                generateFragmentNumber.toString())
        fragmentTransaction.addToBackStack(null) // 戻るボタンでreplace前に戻る
        fragmentTransaction.commit()
    }

    override fun onFragmentBack() {
        mFragmentManager!!.popBackStack()
        generateFragmentNumber--
    }

    companion object {

        private val TAG = PageFragment::class.java.name
    }
}// Required empty public constructor
