package com.bell.twitter.assignment.search.search

import android.content.Context
import android.widget.Toast
import com.bell.twitter.assignment.details.TweetDetailsActivity
import com.bell.twitter.assignment.maps.MapActivity

class SearchRouter
constructor(
    private var context: Context?
) : SearchContract.Router {


    override fun cleanUp() {
        context = null
    }

    override fun openTweetDetailsPage(tweetId: Long) {
        context?.let {
            it.startActivity(TweetDetailsActivity.getStartIntent(it, tweetId))
        }
    }

    override fun openMapsPage() {
        context?.let {
            it.startActivity(MapActivity.getStartIntent(it))
        }
    }

    override fun showError(errorMessage: String?) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}