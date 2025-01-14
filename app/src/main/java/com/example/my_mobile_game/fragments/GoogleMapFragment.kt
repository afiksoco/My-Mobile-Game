package com.example.my_mobile_game.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.my_mobile_game.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapFragment : Fragment(R.layout.fragment_map) {

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            googleMap = map
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
        }
        return view
    }

    fun zoom(lat: Double, lon: Double) {
        if (lat == 0.0 && lon == 0.0) {
            Toast.makeText(context, "Player location is unavailable", Toast.LENGTH_SHORT).show()
            googleMap.clear()
            return
        }

        val location = LatLng(32.115258, 34.817827)
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(location).title("Player Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

}