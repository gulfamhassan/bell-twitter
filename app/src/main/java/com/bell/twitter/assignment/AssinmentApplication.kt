package com.bell.twitter.assignment

import android.app.Application
import android.util.Log
import com.bell.twitter.assignment.R
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

class AssinmentApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(
                TwitterAuthConfig(
                    resources.getString(R.string.TWITTER_CONSUMER_KEY),
                    resources.getString(R.string.TWITTER_CONSUMER_SECRET_KEY)
                )
            )
            .debug(true)
            .build()
        Twitter.initialize(config)
    }
}