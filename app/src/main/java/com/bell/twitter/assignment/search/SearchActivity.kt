package com.bell.twitter.assignment.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.search.search.SearchFragment

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_container, SearchFragment.newInstance())
            .commit()
    }

    companion object {
        @JvmStatic
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
                .apply {
                }
        }
    }
}