package com.bell.twitter.assignment.search.search

import com.bell.twitter.assignment.common.TweetsReceivedListener
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Search

class SearchInteractor : SearchContract.Interactor, Callback<Search>() {

    private var callback: TweetsReceivedListener? = null

    override fun cleanUp() {

    }

    override fun fetchQueryTweetsList(query: String, callback: TweetsReceivedListener) {
        this.callback = callback
        try {
            TwitterCore.getInstance().apiClient.searchService.tweets(
                query, null, null, null,
                null, 100, null, null, null, true
            ).enqueue(this)
        } catch (exp: Exception) {
            exp.printStackTrace()
        }
    }

    override fun success(result: Result<Search>?) {
        val tweetsList = (result?.response?.body() as Search).tweets ?: emptyList()
        callback?.onTweetReceivedSuccess(tweetsList.toMutableList())
    }

    override fun failure(exception: TwitterException?) {
        exception?.printStackTrace()
        callback?.onTweetReceivedFail()
    }
}