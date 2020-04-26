package com.harshithakv.weatherandnewsinfo.ui.news

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.harshithakv.weatherandnewsinfo.R
import kotlinx.android.synthetic.main.activity_news.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsActivity : AppCompatActivity() {

    private val newsViewModel by viewModel<NewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news)
        super.onCreate(savedInstanceState)
        newsViewModel.let{ newsVM ->
            loadUrl(newsVM?.getUrl().toString())
        }
    }

    fun loadUrl(url:String){
        Log.i("demo news activity", url)
        webView?.loadUrl(url)
    }


    }

