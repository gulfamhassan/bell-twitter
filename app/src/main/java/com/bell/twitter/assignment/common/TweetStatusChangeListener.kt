package com.bell.twitter.assignment.common

import com.twitter.sdk.android.core.models.Tweet

interface TweetStatusChangeListener {

    fun onTweetStatusChange(tweet: Tweet)
}