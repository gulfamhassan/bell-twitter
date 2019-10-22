package com.bell.twitter.assignment.search.search

import android.content.Context
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.common.TweetsReceivedListener
import com.bell.twitter.assignment.utils.Constants.Companion.DEFAULT_QUERY_PARAM
import com.bell.twitter.assignment.utils.isNetworkAvailable
import com.twitter.sdk.android.core.models.Tweet

class SearchPresenter
constructor(
    private var context: Context?,
    private var view: SearchContract.View?,
    private var interactor: SearchContract.Interactor?,
    private var router: SearchContract.Router?
) : SearchContract.Presenter, TweetsReceivedListener, OnTweetListItemClickListener {

    override fun onInitializeRequested() {
        fetchQueryTweetsList(DEFAULT_QUERY_PARAM)
    }

    override fun performSearchWithQuery(query: String) {
        fetchQueryTweetsList(if(query.isNotEmpty()) query else DEFAULT_QUERY_PARAM)
    }

    override fun onTweetReceivedSuccess(tweetsList: MutableList<Tweet>?) {
        view?.setTweetsList(tweetsList)
    }

    override fun onTweetReceivedFail() {
        router?.showError(context?.getString(R.string.error_message_internet))
    }

    override fun onTweetItemClick(tweetId: Long) {
        router?.openTweetDetailsPage(tweetId)
    }

    private fun fetchQueryTweetsList(query: String) {
        context?.let {
            if (it.isNetworkAvailable()) {
                interactor?.fetchQueryTweetsList(query, this)
            } else {
                router?.showError(it.getString(R.string.error_message_internet))
            }
        }
    }

    override fun openMapsPage() {
        router?.openMapsPage()
    }

    override fun onCleanUpRequested() {

        interactor?.cleanUp()
        router?.cleanUp()
        context = null
        view = null
        interactor = null
        router = null
    }
}

interface OnTweetListItemClickListener {

    fun onTweetItemClick(tweetId: Long)
}