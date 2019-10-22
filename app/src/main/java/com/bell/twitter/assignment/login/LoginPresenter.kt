package com.bell.twitter.assignment.login

import com.twitter.sdk.android.core.*


class LoginPresenter
constructor(
    private var view: LoginContract.View?,
    private var interactor: LoginContract.Interactor?,
    private var router: LoginContract.Router?
) : LoginContract.Presenter, Callback<TwitterSession>() {

    private lateinit var twitterSession: TwitterSession


    override fun onInitializeRequested() {

    }

    override fun onCleanUpRequested() {
        view = null
        interactor = null
        router = null
    }

    override fun success(result: Result<TwitterSession>) {
        twitterSession = TwitterCore.getInstance().sessionManager.activeSession
        router?.openSearchPage()
    }

    override fun failure(exception: TwitterException) {

    }
}