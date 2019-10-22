package com.bell.twitter.assignment.maps.maps.mapsadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.maps.maps.MapContract
import com.bell.twitter.assignment.utils.Utils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.layout_tweet_marker.view.*
import org.json.JSONObject

class MapAdapter(private val context: Context, private val presenter: MapContract.Presenter?) :
    GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

    companion object {
        const val AVATAR_URL = "profileImageUrl"
        const val TWEET_ID = "id"
        const val TWEET_TEXT = "text"
        const val PLACE_NAME = "placeName"
        const val TWEET_TIME = "time"
    }

    override fun getInfoContents(marker: Marker?): View {
        val markerView = LayoutInflater.from(context).inflate(R.layout.layout_tweet_marker, null)
        val markerData = getMarkerData(marker)
        markerView.userName.text = marker?.title
        markerView.placeName.text = markerData.getString(PLACE_NAME)
        markerView.tweetTimeStamp.text =
            Utils.formatTime(markerData.getString(TWEET_TIME))
        return markerView
    }

    override fun onInfoWindowClick(marker: Marker?) {
        val tweetId = getMarkerData(marker).getLong(TWEET_ID)
        presenter?.onPinClick(tweetId)
    }

    override fun getInfoWindow(marker: Marker?) = null

    private fun getMarkerData(marker: Marker?): JSONObject {
        marker?.snippet?.let {
            return JSONObject(it)
        }
        return JSONObject()
    }
}