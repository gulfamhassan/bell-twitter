package com.bell.twitter.assignment.details.details

import android.content.Context
import com.bell.twitter.assignment.media.ViewMediaActivity

class TweetDetailsRouter
constructor(
    private var context: Context?
) : TweetDetailsContract.Router {


    override fun cleanUp() {
        context = null
    }

    override fun openViewMediaPage(mediaType: String, mediaUrl: String, mediaContentTye: String) {
        context?.let {
            it.startActivity(
                ViewMediaActivity.getStartIntent(
                    it,
                    mediaType,
                    mediaUrl,
                    mediaContentTye
                )
            )
        }
    }
}