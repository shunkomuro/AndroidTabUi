package com.example.komuroshun.androidtabui.weather

import android.content.Context
import android.content.Intent.getIntent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.komuroshun.androidtabui.BaseFragment

import com.example.komuroshun.androidtabui.R
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlinx.android.synthetic.main.fragment_weather_info.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_CITY_NAME = "param1"
private const val ARG_CITY_ID = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WeatherInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WeatherInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WeatherInfoFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var cityName: String? = null
    private var cityId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityName = it.getString(ARG_CITY_NAME)
            cityId = it.getString(ARG_CITY_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val receiver = WeatherInfoReceiver(cityName!!, tvCityName, tvWeatherTelop, tvWeatherDesc)
        receiver.execute(cityId)
    }

    companion object {
        /**
         * @param cityName Parameter 1.
         * @param cityId Parameter 2.
         * @return A new instance of fragment WeatherInfoFragment.
         */
        @JvmStatic
        fun newInstance(cityName: String, cityId: String) =
                WeatherInfoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_CITY_NAME, cityName)
                        putString(ARG_CITY_ID, cityId)
                    }
                }
    }

    /**
     * 非同期でお天気データを取得するクラス。
     *
     * @author Shinzo SAITO
     */
    private inner class WeatherInfoReceiver
    /**
     * コンストラクタ。
     * 都市名とお天気情報を表示する画面部品をあらかじめ取得してフィールドに格納している。
     *
     * @param cityName 都市名。
     * @param tvCityName 都市名を表示する画面部品。
     * @param tvWeatherTelop 現在の天気を表示する画面部品。
     * @param tvWeatherDesc 天気の詳細を表示する画面部品。
     */
    (
            /**
             * 都市名文字列フィールド。
             */
            private val _cityName: String,
            /**
             * 都市名を表示する画面部品フィールド。
             */
            private val _tvCityName: TextView,
            /**
             * 現在の天気を表示する画面部品フィールド。
             */
            private val _tvWeatherTelop: TextView,
            /**
             * 天気の詳細を表示する画面部品フィールド。
             */
            private val _tvWeatherDesc: TextView) : AsyncTask<String, String, String>() {

        public override fun doInBackground(vararg params: String): String {
            val id = params[0]
            val urlStr = "http://weather.livedoor.com/forecast/webservice/json/v1?city=$id"

            var con: HttpURLConnection? = null
            var `is`: InputStream? = null
            var result = ""

            try {
                val url = URL(urlStr)
                con = url.openConnection() as HttpURLConnection
                con.requestMethod = "GET"
                con.connect()
                `is` = con.inputStream

                result = is2String(`is`)
            } catch (ex: MalformedURLException) {
            } catch (ex: IOException) {
            } finally {
                con?.disconnect()

                if (`is` != null) {
                    try {
                        `is`.close()
                    } catch (ex: IOException) {
                    }

                }
            }

            return result
        }

        public override fun onPostExecute(result: String) {
            var desc = ""
            var dateLabel = ""
            var telop = ""
            try {
                val rootJSON = JSONObject(result)
                val descriptionJSON = rootJSON.getJSONObject("description")
                desc = descriptionJSON.getString("text")
                val forecasts = rootJSON.getJSONArray("forecasts")
                val forecastNow = forecasts.getJSONObject(0)
                dateLabel = forecastNow.getString("dateLabel")
                telop = forecastNow.getString("telop")
            } catch (ex: JSONException) {
                Log.e("Exception", ex.toString())
            }

            _tvCityName.text = _cityName + "の" + dateLabel + "の天気: "
            _tvWeatherTelop.text = telop
            _tvWeatherDesc.text = desc
        }

        /**
         * InputStreamオブジェクトを文字列に変換するメソッド。変換文字コードはUTF-8。
         *
         * @param is 変換対象のInputStreamオブジェクト。
         * @return 変換された文字列。
         * @throws IOException 変換に失敗した時に発生。
         */
        @Throws(IOException::class)
        private fun is2String(`is`: InputStream?): String {
            val reader = BufferedReader(InputStreamReader(`is`!!, "UTF-8"))
            val sb = StringBuffer()
            val b = CharArray(1024)
            var line: Int
            do {
                line = reader.read(b)
                if (line < 0) {
                    break
                }
                sb.append(b, 0, line)
            } while (true)
            return sb.toString()
        }
    }
}
