package com.bell.twitter.assignment.login

import android.content.Context
import com.bell.twitter.assignment.search.SearchActivity

class LoginRouter
constructor(
    private var context: Context?
) : LoginContract.Router {


    override fun cleanUp() {
        context = null
    }

    override fun openSearchPage() {
        context?.let {
            it.startActivity(SearchActivity.getStartIntent(it))
        }

    }
}