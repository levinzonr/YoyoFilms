package cz.levinzonr.yoyofilms

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import cz.levinzonr.yoyofilms.view.favorites.FavoritesFragment
import cz.levinzonr.yoyofilms.view.search.FilmSearchFragment
import cz.levinzonr.yoyofilms.view.trending.TrendingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val ITEMS = arrayListOf(
                R.string.title_popular,
                R.string.title_favorites,
                R.string.title_search)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_favorites -> {
                view_pager.currentItem = 1
                true
            }
            R.id.navigation_movies -> {
                view_pager.currentItem = 0
                true
            }
            R.id.navigation_search -> {
                view_pager.currentItem = 2
                true
            }
            else -> {
                true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TrendingFragment())
        adapter.addFragment(FavoritesFragment())
        adapter.addFragment(FilmSearchFragment())
        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            private var previous: Int = 0
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                supportActionBar?.title = getString(ITEMS[position])
                navigation.menu.getItem(previous).isChecked = false
                navigation.menu.getItem(position).isChecked = true
                previous = position
            }
        })
    }

    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val fragments = ArrayList<Fragment>()

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size
    }

}
