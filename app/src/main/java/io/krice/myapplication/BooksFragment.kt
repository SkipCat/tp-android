package io.krice.myapplication

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BooksFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_books, container, false)
        return view
    }

    companion object {
        fun newInstance(): BooksFragment {
            val booksInstance = BooksFragment()
            return booksInstance
        }
    }
}