package com.bell.twitter.assignment.details.details

import com.bell.twitter.assignment.common.BaseContract
import com.bell.twitter.assignment.common.TweetStatusChangeListener
import com.twitter.sdk.android.core.models.Tweet

interface TweetDetailsContract : BaseContract {

    interface View : BaseContract.View {
        fun showUpdatedTweet(tweet: Tweet)
    }

    interface Presenter : BaseContract.Presenter {

        fun showTweet(id: Long)
        fun markTweetFavorite(id: Long)
        fun markTweetUnFavorite(id: Long)
        fun markReTweet(id: Long)
        fun markReTweetUndo(id: Long)

        fun openViewMediaPage(mediaType: String, mediaUrl: String, mediaContentTye: String)
    }

    interface Interactor : BaseContract.Interactor {

        fun setTweetStatusListener(callback: TweetStatusChangeListener)
        fun showTweet(id: Long)
        fun markTweetFavorite(id: Long)
        fun markTweetUnFavorite(id: Long)
        fun markReTweet(id: Long)
        fun markReTweetUndo(id: Long)
    }

    interface Router : BaseContract.Router {

        fun openViewMediaPage(mediaType: String, mediaUrl: String, mediaContentTye: String)
    }
}