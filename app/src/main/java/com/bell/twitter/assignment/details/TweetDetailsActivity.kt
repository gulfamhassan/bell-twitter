package com.bell.twitter.assignment.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.details.details.TweetDetailsFragment

class TweetDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet_details)

        val tweetId: Long = intent?.extras?.getLong(TWEET_EXTRA_KEY) ?: 0L
        supportFragmentManager.beginTransaction()
            .replace(R.id.details_container, TweetDetailsFragment.newInstance(tweetId))
            .commit()
    }

    companion object {
        const val TWEET_EXTRA_KEY: String = "tweet_key"
        @JvmStatic
        fun getStartIntent(context: Context, tweetId: Long): Intent {
            return Intent(context, TweetDetailsActivity::class.java)
                .apply {
                    putExtra(TWEET_EXTRA_KEY, tweetId)
                }
        }
    }
}