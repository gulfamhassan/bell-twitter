package com.bell.twitter.assignment.maps.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.bell.twitter.assignment.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_maps.*

class MapFragment : Fragment(), MapContract.View, OnMapReadyCallback,
    SeekBar.OnSeekBarChangeListener {


    private lateinit var interactor: MapContract.Interactor
    private lateinit var router: MapContract.Router
    private lateinit var presenter: MapContract.Presenter

    private lateinit var mapFragment: SupportMapFragment
    private var updatedRadius: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seekBar.setOnSeekBarChangeListener(this)

        interactor = MapInteractor()
        router = MapRouter(context)
        presenter = MapPresenter(context, this, interactor, router)
        presenter.onInitializeRequested()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.google_maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            presenter.setGoogleMaps(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onCleanUpRequested()
    }


    override fun onProgressChanged(seekBar: SeekBar?, seekProgress: Int, isUser: Boolean) {

        updatedRadius = (seekProgress * 1000.0)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        radiusText.text = "${updatedRadius / 1000} KM"
        presenter.loadTweetsAsPerUpdateRadius(updatedRadius)
    }


    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
            .apply {
                arguments = Bundle().apply {
                }
            }
    }
}