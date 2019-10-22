package com.bell.twitter.assignment.maps.maps

import com.bell.twitter.assignment.common.BaseContract
import com.bell.twitter.assignment.common.TweetsReceivedListener
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

interface MapContract : BaseContract {

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter {

        fun setGoogleMaps(map: GoogleMap)
        fun loadTweetsAsPerUpdateRadius(radius: Double)
        fun onPinClick(tweetId: Long)
    }

    interface Interactor : BaseContract.Interactor {

        fun fetchNearbyTweets(latLng: LatLng, radius: Float, callback: TweetsReceivedListener)
    }

    interface Router : BaseContract.Router {

        fun openTweetDetailsPage(tweetId: Long)
        fun showError(errorMessage: String)
    }
}