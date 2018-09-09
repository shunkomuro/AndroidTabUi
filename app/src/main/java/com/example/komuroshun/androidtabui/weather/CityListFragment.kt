package com.example.komuroshun.androidtabui.weather

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import com.example.komuroshun.androidtabui.BaseFragment
import kotlinx.android.synthetic.main.fragment_city_list.*

import com.example.komuroshun.androidtabui.R
import com.example.komuroshun.androidtabui.viewmodel.WeatherInfoViewModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * This Fragment display city's weather info list.
 */
class CityListFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = ArrayList<Map<String, String>>()
        var map: MutableMap<String, String> = HashMap()
        map["name"] = "大阪"
        map["id"] = "270000"
        list.add(map)
        map = HashMap()
        map["name"] = "神戸"
        map["id"] = "280010"
        list.add(map)
        map = HashMap()
        map["name"] = "豊岡"
        map["id"] = "280020"
        list.add(map)
        map = HashMap()
        map["name"] = "京都"
        map["id"] = "260010"
        list.add(map)
        map = HashMap()
        map["name"] = "舞鶴"
        map["id"] = "260020"
        list.add(map)
        map = HashMap()
        map["name"] = "奈良"
        map["id"] = "290010"
        list.add(map)
        map = HashMap()
        map["name"] = "風屋"
        map["id"] = "290020"
        list.add(map)
        map = HashMap()
        map["name"] = "和歌山"
        map["id"] = "300010"
        list.add(map)
        map = HashMap()
        map["name"] = "潮岬"
        map["id"] = "300020"
        list.add(map)

        val from = arrayOf("name")
        val to = intArrayOf(android.R.id.text1)
        val adapter = SimpleAdapter(activity, list, android.R.layout.simple_expandable_list_item_1, from, to)

        cityList.adapter = adapter
        cityList.onItemClickListener = ListItemClickListener()
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {

        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val item = parent.getItemAtPosition(position) as Map<String, String>
            val cityName = item["name"]
            val cityId = item["id"]
            mListener!!.onFragmentInteraction(cityName, cityId)
//            mListener!!.onFragmentInteraction()
//            val intent = Intent(activity, WeatherInfoActivity::class.java)
//            intent.putExtra("cityName", cityName)
//            intent.putExtra("cityId", cityId)
//            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CityListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CityListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
