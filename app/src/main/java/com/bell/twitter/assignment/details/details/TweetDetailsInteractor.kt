package com.bell.twitter.assignment.details.details

import com.bell.twitter.assignment.common.TweetStatusChangeListener
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet

class TweetDetailsInteractor : TweetDetailsContract.Interactor, Callback<Tweet>() {

    private var callback: TweetStatusChangeListener? = null
    override fun cleanUp() {

    }

    override fun setTweetStatusListener(callback: TweetStatusChangeListener) {
        this.callback = callback
    }


    override fun showTweet(id: Long) {
        TwitterCore.getInstance().apiClient.statusesService.show(id, null, null, null).enqueue(this)
    }

    override fun markTweetFavorite(id: Long) {
        TwitterCore.getInstance().apiClient.favoriteService.create(id, null).enqueue(this)

    }

    override fun markTweetUnFavorite(id: Long) {
        TwitterCore.getInstance().apiClient.favoriteService.destroy(id, null).enqueue(this)
    }

    override fun markReTweet(id: Long) {
        TwitterCore.getInstance().apiClient.statusesService.retweet(id, null).enqueue(this)
    }

    override fun markReTweetUndo(id: Long) {
        TwitterCore.getInstance().apiClient.statusesService.unretweet(id, null).enqueue(this)
    }

    override fun success(result: Result<Tweet>?) {
        val tweet = result?.response?.body() as Tweet
        callback?.onTweetStatusChange(tweet)
    }

    override fun failure(exception: TwitterException?) {

    }


}