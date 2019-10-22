package com.bell.twitter.assignment.maps.maps

import android.content.Context
import android.widget.Toast
import com.bell.twitter.assignment.details.TweetDetailsActivity

class MapRouter
constructor(
    private var context: Context?
) : MapContract.Router {


    override fun cleanUp() {
        context = null
    }

    override fun openTweetDetailsPage(tweetId: Long) {
        context?.let {
            it.startActivity(TweetDetailsActivity.getStartIntent(it, tweetId))
        }
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}