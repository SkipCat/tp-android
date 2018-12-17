package io.krice.myapplication

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.krice.myapplication.News

class NewsListViewModel : ViewModel() {
    private var newsList :LiveData<List<News>>? = null

    fun getNews() :LiveData<List<News>>{
        if (newsList == null){
            newsList = NewsListLiveData()
        }

        return newsList as LiveData<List<News>>
    }
}