package io.krice.myapplication

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class HomeActivity : AppCompatActivity() {

    private val mNewsFragment = NewsFragment.newInstance()
    private val mBooksFragment = BooksFragment.newInstance()
    private val mClubFragment = ClubFragment.newInstance()
    private val mSettingsFragment = SettingsFragment.newInstance()
    private val mForumFragment = ForumFragment.newInstance()

    private var mCurrentFragmentId = R.id.menu_news

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        updateFragment(mNewsFragment)

        val menu = findViewById<BottomNavigationView>(R.id.home_bottom_nv)
        menu.setOnNavigationItemSelectedListener {
            val selectedFragmentId = it.itemId
            if (selectedFragmentId == mCurrentFragmentId) {
                return@setOnNavigationItemSelectedListener false
            }
            mCurrentFragmentId = selectedFragmentId
            when (selectedFragmentId) {
                R.id.menu_news -> {
                    updateToolBarTitle(R.string.news)
                    updateFragment(mNewsFragment)
                }
                R.id.menu_forum -> {
                    updateToolBarTitle(R.string.forum)
                    updateFragment(mForumFragment)
                }
                R.id.menu_books -> {
                    updateToolBarTitle(R.string.books)
                    updateFragment(mBooksFragment)
                }
                R.id.menu_club_igen -> {
                    updateToolBarTitle(R.string.club)
                    updateFragment(mClubFragment)
                }
                else ->  {
                    updateToolBarTitle(R.string.settings)
                    updateFragment(mSettingsFragment)
                }
            }
        }

    }

    private fun updateToolBarTitle(stringId: Int) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(stringId)
    }

    private fun updateFragment(fragment: Fragment): Boolean {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_container_view, fragment)
                .commit()

        return true
    }
}
