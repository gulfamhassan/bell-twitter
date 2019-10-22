package com.bell.twitter.assignment.search.search

import com.bell.twitter.assignment.common.BaseContract
import com.bell.twitter.assignment.common.TweetsReceivedListener
import com.twitter.sdk.android.core.models.Tweet

interface SearchContract : BaseContract {

    interface View : BaseContract.View {
        fun setTweetsList(tweetsList: MutableList<Tweet>?)
    }

    interface Presenter : BaseContract.Presenter {
        fun performSearchWithQuery(query: String)
        fun openMapsPage()
    }

    interface Interactor : BaseContract.Interactor {
        fun fetchQueryTweetsList(query: String, callback: TweetsReceivedListener)
    }

    interface Router : BaseContract.Router {

        fun openTweetDetailsPage(tweetId: Long)
        fun openMapsPage()
        fun showError(errorMessage: String?)
    }
}