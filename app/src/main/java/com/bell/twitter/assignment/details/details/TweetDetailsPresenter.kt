package com.bell.twitter.assignment.details.details

import com.bell.twitter.assignment.common.TweetStatusChangeListener
import com.twitter.sdk.android.core.models.Tweet

class TweetDetailsPresenter
constructor(
    private var view: TweetDetailsContract.View?,
    private var interactor: TweetDetailsContract.Interactor?,
    private var router: TweetDetailsContract.Router?
) : TweetDetailsContract.Presenter, TweetStatusChangeListener {

    override fun onInitializeRequested() {
        interactor?.setTweetStatusListener(this)
    }

    override fun onCleanUpRequested() {

        interactor?.cleanUp()
        router?.cleanUp()
        view = null
        interactor = null
        router = null
    }

    override fun onTweetStatusChange(tweet: Tweet) {
        view?.showUpdatedTweet(tweet)
    }

    override fun showTweet(id: Long) {
        interactor?.showTweet(id)
    }

    override fun markTweetFavorite(id: Long) {
        interactor?.markTweetFavorite(id)
    }

    override fun markTweetUnFavorite(id: Long) {
        interactor?.markTweetUnFavorite(id)
    }

    override fun markReTweet(id: Long) {
        interactor?.markReTweet(id)
    }

    override fun markReTweetUndo(id: Long) {
        interactor?.markReTweetUndo(id)
    }

    override fun openViewMediaPage(mediaType: String, mediaUrl: String, mediaContentTye: String) {
        router?.openViewMediaPage(mediaType, mediaUrl, mediaContentTye)
    }

}