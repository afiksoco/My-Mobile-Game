package com.example.my_mobile_game.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.my_mobile_game.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Set default location (e.g., world view)
        val defaultLocation = LatLng(0.0, 0.0)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 2f))
    }

    fun zoom(lat: Double, lon: Double) {
        val location = LatLng(lat, lon)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
        googleMap?.addMarker(MarkerOptions().position(location).title("Selected Score"))
    }
}
