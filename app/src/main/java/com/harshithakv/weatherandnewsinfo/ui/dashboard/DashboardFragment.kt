package com.harshithakv.weatherandnewsinfo.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harshithakv.openweathernews.models.Article
import com.harshithakv.openweathernews.models.EverythingNews
import com.harshithakv.openweathernews.ui.adapters.TopNewsAdapter
import com.harshithakv.weatherandnewsinfo.R
import com.harshithakv.weatherandnewsinfo.models.ArticleTop
import com.harshithakv.weatherandnewsinfo.models.TopHeadlines
import com.harshithakv.weatherandnewsinfo.ui.adapters.AllNewsAdapter
import com.harshithakv.weatherandnewsinfo.ui.news.NewsActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment(), AllNewsAdapter.OnNewsListener {
    private val dashboardViewModel by viewModel<DashboardViewModel>()

    private var mAdapterAll: RecyclerView.Adapter<*>? = null
    private var layoutManagerAll: RecyclerView.LayoutManager? = null
    lateinit var newsList: ArrayList<ArticleTop>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview_dash?.setHasFixedSize(true)
        layoutManagerAll = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        subscribeObservers()
    }




    fun subscribeObservers(){
        dashboardViewModel.let{dashVM ->
            dashVM.getAllNews()

            dashVM?.allNewsLiveData()?.observe(viewLifecycleOwner, Observer<TopHeadlines?> { topNews ->
                topNews?.let { displayNews(it as TopHeadlines) }
            })
        }

    }
    fun displayNews(topHeadlines: TopHeadlines) {
        newsList = ArrayList<ArticleTop>()
        for (i in 0 until topHeadlines.articlesTop.size) {
            newsList.add(topHeadlines.articlesTop.get(i))
        }
        mAdapterAll = AllNewsAdapter(newsList, this)
        recyclerview_dash?.adapter = mAdapterAll
        recyclerview_dash?.layoutManager = layoutManagerAll
        mAdapterAll!!.notifyDataSetChanged()


    }

    override fun onNewsClick(position: Int) {
        dashboardViewModel?.pushUrl(newsList[position].url)
        Log.i("demo", "Clicked "+ newsList[position].url)
        val intent = Intent(getActivity(), NewsActivity::class.java)
        startActivity(intent)

    }

    fun imageLoader(icon: String): String? {
        return "http://openweathermap.org/img/wn/$icon@2x.png"
    }

}