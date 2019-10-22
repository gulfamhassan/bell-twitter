package com.bell.twitter.assignment.maps.maps

import com.bell.twitter.assignment.common.TweetsReceivedListener
import com.google.android.gms.maps.model.LatLng
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.services.params.Geocode

class MapInteractor : MapContract.Interactor, Callback<Search>() {

    private var callback: TweetsReceivedListener? = null
    override fun cleanUp() {

    }

    override fun fetchNearbyTweets(
        latLng: LatLng,
        radius: Float,
        callback: TweetsReceivedListener
    ) {

        this.callback = callback
        try {
            val geoCode = Geocode(
                latLng.latitude,
                latLng.longitude,
                radius.toInt() / 1000,
                Geocode.Distance.KILOMETERS
            )
            TwitterCore.getInstance().apiClient.searchService.tweets(
                "#food", geoCode, null, null,
                null, 100, null, null, null, true
            ).enqueue(this)

        } catch (e: Exception) {
            e.printStackTrace()
            callback.onTweetReceivedFail()
        }

    }

    override fun success(result: Result<Search>?) {
        val tweets = (result?.response?.body() as Search).tweets
        callback?.onTweetReceivedSuccess(tweets.toMutableList())
    }

    override fun failure(exception: TwitterException?) {
        exception?.printStackTrace()
        callback?.onTweetReceivedFail()
    }
}