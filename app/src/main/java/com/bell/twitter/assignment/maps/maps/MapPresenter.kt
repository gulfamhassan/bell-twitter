package com.bell.twitter.assignment.maps.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.common.TweetsReceivedListener
import com.bell.twitter.assignment.maps.maps.mapsadapter.MapAdapter
import com.bell.twitter.assignment.utils.Constants.Companion.DEFAULT_RADIUS
import com.bell.twitter.assignment.utils.Constants.Companion.DEFAULT_ZOOM_SCALE
import com.bell.twitter.assignment.utils.getLatLng
import com.bell.twitter.assignment.utils.getZoomLevel
import com.bell.twitter.assignment.utils.isNetworkAvailable
import com.bell.twitter.assignment.utils.toJSONObjectForPin
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.twitter.sdk.android.core.models.Tweet

class MapPresenter
constructor(
    private var context: Context?,
    private var view: MapContract.View?,
    private var interactor: MapContract.Interactor?,
    private var router: MapContract.Router?
) : MapContract.Presenter, TweetsReceivedListener {

    private lateinit var googleMap: GoogleMap
    private lateinit var latLng: LatLng
    private var location: Location? = null
    private lateinit var circle: Circle
    private var circleRadius = DEFAULT_RADIUS

    override fun onInitializeRequested() {
        location = getCurrentLocation()
    }

    override fun setGoogleMaps(map: GoogleMap) {
        googleMap = map
        location?.let {
            latLng = LatLng(it.latitude, it.longitude)
        } ?: run {
            latLng = LatLng(-34.0, 151.0)
        }

        setMapZoom(latLng)
        setMapAdapter()
        loadNearbyTweets(latLng, circleRadius.toFloat(), this)
    }

    override fun loadTweetsAsPerUpdateRadius(radius: Double) {
        circleRadius = radius
        loadNearbyTweets(latLng, circleRadius.toFloat(), this)
    }

    override fun onTweetReceivedSuccess(tweetsList: MutableList<Tweet>?) {
        showTweetsOnMap(tweetsList, latLng)
    }

    override fun onTweetReceivedFail() {
        context?.let {
            router?.showError(it.getString(R.string.error_message_tweet_load))
        }
    }

    override fun onPinClick(tweetId: Long) {
        router?.openTweetDetailsPage(tweetId)
    }


    private fun setMapZoom(latLng: LatLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(
                    latLng,
                    DEFAULT_ZOOM_SCALE
                )
            )
        )
        googleMap.uiSettings.isMyLocationButtonEnabled = false
    }

    private fun setMapAdapter() {
        context?.let {
            val adapter = MapAdapter(it, this)
            googleMap.setInfoWindowAdapter(adapter)
            googleMap.setOnInfoWindowClickListener(adapter)
        }
    }

    private fun loadNearbyTweets(latLng: LatLng, radius: Float, callback: TweetsReceivedListener) {

        context?.let {
            if (it.isNetworkAvailable()) {
                interactor?.fetchNearbyTweets(latLng, radius, callback)
            } else {
                router?.showError(it.getString(R.string.error_message_internet))
            }
        }
    }

    private fun showTweetsOnMap(tweetsList: MutableList<Tweet>?, latLng: LatLng) {
        googleMap.clear()
        addMapCircle(latLng)
        tweetsList?.let {
            it.map { tweet ->
                tweet.getLatLng()?.let { latLng ->
                    Pair(tweet, latLng)
                }
            }.forEach { pair: Pair<Tweet, LatLng>? ->
                pair?.let {
                    addMarker(pair)
                }
            }
        }
    }

    private fun addMapCircle(latLong: LatLng) {
        circle = googleMap.addCircle(
            CircleOptions()
                .center(latLong)
                .radius(circleRadius)
                .strokeWidth(2f)
                .fillColor(0x220000FF)
        )
        googleMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(
                    latLong,
                    circle.getZoomLevel()
                )
            )
        )
    }

    private fun addMarker(tweet: Pair<Tweet, LatLng>) {
        val json = tweet.first.toJSONObjectForPin()
        googleMap.addMarker(
            MarkerOptions().position(tweet.second)
                .title(tweet.first.user.name)
                .snippet(json.toString())
        )
    }

    private fun getCurrentLocation(): Location? {
        context?.let {
            val locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            } else {
                return null
            }
        }
        return null
    }

    override fun onCleanUpRequested() {

        interactor?.cleanUp()
        router?.cleanUp()
        context = null
        view = null
        interactor = null
        router = null
    }
}