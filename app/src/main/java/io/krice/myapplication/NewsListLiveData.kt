package io.krice.myapplication

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import io.krice.myapplication.News
import io.krice.myapplication.addNonNull
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_news.*
import java.text.SimpleDateFormat
import java.util.*

class NewsListLiveData : MutableLiveData<List<News>>() {

    override fun onActive() {
        val database = FirebaseDatabase.getInstance()

        val listener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val news = mutableListOf<News>()

                for (item in dataSnapshot.children) {
                    news.addNonNull(item.getValue(News::class.java))
                }

                postValue(news)
            }

            override fun onCancelled(dbErr: DatabaseError) {
                Log.e("warning", "database error ${dbErr.message}")
            }
        }

        database.getReference("News").addValueEventListener(listener)
    }

    override fun onInactive() {
        //tt stopper
    }
}