package io.krice.myapplication

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.list_item_news.view.*
import android.content.Intent
import android.net.Uri


class NewsFragment : Fragment(), NewsAdapter.OnNewsItemClickListener {
    private lateinit var adapter: NewsAdapter

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                NewsFragment().apply {
                    arguments = Bundle().apply {
                        //                        putString(stringResource, param1)
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NewsAdapter(listener = this)

        news_list.setHasFixedSize(true)
        news_list.layoutManager = LinearLayoutManager(context)
        news_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        news_list.adapter = adapter

        fetchNewsFromFirebase()
    }

    private fun fetchNewsFromFirebase() {
        val vm = ViewModelProviders.of(this).get(NewsListViewModel::class.java)

        vm.getNews().observe(this, Observer<List<News>>{
            if (it != null) {
                adapter.updateContent(it)

                news_progress_bar.visibility = View.GONE
            } else {
                news_progress_bar.visibility = View.VISIBLE
            }
        })

    }

    override fun onNewsClicked(news: News) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(news.link))
        startActivity(i)
    }
}

private class NewsAdapter(private val list: MutableList<News> = mutableListOf(),
                          private var listener: OnNewsItemClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateContent(list: List<News>) {
        this.list.replaceContent(list)
        notifyDataSetChanged()
    }

    fun updateContentWithDiffUtil(list: List<News>) {
        val oldList = this.list.toMutableList()
        this.list.replaceContent(list)
        DiffUtil.calculateDiff(NewsDiff(oldList = oldList, newList = list)).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsItemViewHolder(
                LayoutInflater.from(container.context)
                        .inflate(R.layout.list_item_news, container, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsItemViewHolder) {
            val news = list[position]
            holder.bindNews(news)

            holder.itemView.setOnClickListener { listener?.onNewsClicked(news) }
        }
    }

    override fun getItemCount(): Int = list.size

    interface OnNewsItemClickListener {
        fun onNewsClicked(news: News)
    }

}

class NewsItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val picture: AppCompatImageView = v.news_item_thumbnail
    private val title: AppCompatTextView = v.news_item_title
    private val subtitle: AppCompatTextView = v.news_item_subtitle
    private val summary: AppCompatTextView = v.news_item_excerpt

    @SuppressLint("SetTextI18n")
    fun bindNews(newsItem: News) {
        title.text = newsItem.title
        subtitle.text = "${newsItem.author} ${newsItem.date}" // TODO Replace with a resource
        summary.text = newsItem.summary
    }

}

private class NewsDiff(val oldList: List<News>, val newList: List<News>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean = oldList[p0] == newList[p1]

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean = oldList[p0] == newList[p1]

}

fun <T> MutableList<T>.replaceContent(list: List<T>) {
    clear()
    addAll(list)
}

fun <T> MutableList<T>.addNonNull(item: T?) {
    if (item != null) this.add(item)
}