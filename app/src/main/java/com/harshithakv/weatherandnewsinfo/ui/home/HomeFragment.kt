package com.harshithakv.weatherandnewsinfo.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harshithakv.openweathernews.models.*
import com.harshithakv.openweathernews.ui.adapters.TopNewsAdapter
import com.harshithakv.weatherandnewsinfo.R
import com.harshithakv.weatherandnewsinfo.ui.news.NewsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() ,TopNewsAdapter.OnNewsListener {
    private val homeViewModel by viewModel<HomeViewModel>()

    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var newsArrayList: ArrayList<Article>
    lateinit var preferences: SharedPreferences

    lateinit var city:String
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferences =PreferenceManager.getDefaultSharedPreferences(context)
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val editor: SharedPreferences.Editor = preferences.edit()
//        editor.putString("city","New York");
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        city = preferences.getString("city","New York").toString()
        subscribeObservers(city)
        initSearchView();
    }

    fun subscribeObservers(city: String) {
        homeViewModel.let { weatherVM ->
            weatherVM?.getCurrWeather(city)
            weatherVM?.getForecastWeather(city)
            weatherVM?.getCityNews(city)

            weatherVM?.getWeatherInfo()?.observe(viewLifecycleOwner, Observer<CurrentWeather?> { currentWeather ->
                currentWeather?.let { displayCurrent(it as CurrentWeather) }
            })

            weatherVM?.getForecastInfo()
                ?.observe(viewLifecycleOwner, Observer<WeatherForecast?> { weatherForecast ->
                    if (weatherForecast != null) {
                        var forecastList: ArrayList<Listt> =
                            weatherForecast.list as ArrayList<Listt>
                        var maxMap: HashMap<String?, Int?> = HashMap()
                        var maxIcon = HashMap<String?, String?>()
                        var minMaps = HashMap<String?, Int?>()
                        var forecastDays = ArrayList<String?>()

                        maxMap =
                            weatherVM.calculateMax(forecastList, maxMap, maxIcon)
                        minMaps = weatherVM.calculateMin(forecastList, minMaps)
                        weatherVM.nextdays(forecastDays)
                        displayForecast(forecastDays, maxMap, minMaps, maxIcon)
                    }
                })

            weatherVM?.getTopNewsInfo()?.observe(viewLifecycleOwner, Observer<EverythingNews?> { topNews ->
                topNews?.let { displayNews(it as EverythingNews) }
            })
        }
    }

    fun displayCurrent(currentWeather: CurrentWeather) {
        tv_city.text = currentWeather.name
        tv_curr.text = "Current " + currentWeather.main.temp.toInt()
        tv_max_min.text =
            "Max " + currentWeather.main.tempMax.toInt() + " / Min " + currentWeather.main.tempMin.toInt()
        Picasso.get()
            .load(homeViewModel?.imageLoader(currentWeather.weather.get(0).icon))
            .resize(250, 250).centerCrop().into(iv_icon)
    }

    fun displayForecast(
        forecastDays: List<String?>,
        Maxmaps: HashMap<String?, Int?>,
        Minmaps: HashMap<String?, Int?>,
        maxIcon: HashMap<String?, String?>
    ) {
        tv_nextOne.text =
            Maxmaps.get(forecastDays[0]).toString() + " / " + Minmaps[forecastDays[0]]
        tv_nextTwo.text =
            Maxmaps.get(forecastDays[1]).toString() + " / " + Minmaps[forecastDays[1]]
        tv_nextThree.text =
            Maxmaps.get(forecastDays[2]).toString() + " / " + Minmaps[forecastDays[2]]
        Picasso.get().load(maxIcon[forecastDays[0]]?.let { homeViewModel?.imageLoader(it) })
            .resize(150, 150).centerCrop().into(iv_one)
        Picasso.get().load(maxIcon[forecastDays[1]]?.let { homeViewModel?.imageLoader(it) })
            .resize(150, 150).centerCrop().into(iv_two)
        Picasso.get().load(maxIcon[forecastDays[2]]?.let { homeViewModel?.imageLoader(it) })
            .resize(150, 150).centerCrop().into(iv_three)
    }

    fun displayNews(everythingNews: EverythingNews) {
        newsArrayList = ArrayList<Article>()
        for (i in 0 until everythingNews.articles.size) {
            newsArrayList.add(everythingNews.articles.get(i))
        }
        mAdapter = TopNewsAdapter(newsArrayList, this)
        recyclerView?.adapter = mAdapter
        recyclerView?.layoutManager = layoutManager
        mAdapter!!.notifyDataSetChanged()
        if (!newsArrayList.isEmpty()) {
            empty_view.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            empty_view.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }

    }


    override fun onNewsClick(position: Int) {
        homeViewModel?.pushUrl(newsArrayList[position].url)
        val intent = Intent(getActivity(), NewsActivity::class.java)
        startActivity(intent)

    }

    fun initSearchView() {
        search_toolbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("city", query);
                editor.apply();
                homeViewModel?.getCurrWeather(query)
                homeViewModel?.getForecastWeather(query)
                homeViewModel?.getCityNews(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

}


