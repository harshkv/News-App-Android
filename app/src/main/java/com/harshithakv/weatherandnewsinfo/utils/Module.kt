package com.harshithakv.weatherandnewsinfo.utils

import com.harshithakv.openweathernews.repository.CurrentWeatherRepo
import com.harshithakv.weatherandnewsinfo.ui.dashboard.DashboardViewModel
import com.harshithakv.weatherandnewsinfo.ui.home.HomeViewModel
import com.harshithakv.weatherandnewsinfo.ui.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModel = module {
    viewModel {
        HomeViewModel(get())
    }
}


val currentWeatherRepo = module {
    single {
        CurrentWeatherRepo
    }
}

val dashboardViewModel = module {
    viewModel {
        DashboardViewModel(get())
    }
}

val newsViewModel = module {
    viewModel {
        NewsViewModel(get())
    }
}