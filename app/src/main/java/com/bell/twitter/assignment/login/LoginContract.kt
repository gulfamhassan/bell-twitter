package com.bell.twitter.assignment.login

import com.bell.twitter.assignment.common.BaseContract

interface LoginContract : BaseContract {

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter {

    }

    interface Interactor : BaseContract.Interactor {

    }

    interface Router : BaseContract.Router {

        fun openSearchPage()
    }
}