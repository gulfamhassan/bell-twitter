package com.bell.twitter.assignment.common

interface BaseContract {

    interface View {

    }

    interface Presenter {
        fun onInitializeRequested()
        fun onCleanUpRequested()
    }

    interface Interactor {
        fun cleanUp()
    }

    interface Router {
        fun cleanUp()
    }
}