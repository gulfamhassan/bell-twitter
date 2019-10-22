package com.bell.twitter.assignment.common

import com.twitter.sdk.android.core.models.Tweet

interface TweetsReceivedListener {

    fun onTweetReceivedSuccess(tweetsList: MutableList<Tweet>?)
    fun onTweetReceivedFail()
}