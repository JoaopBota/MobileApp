package com.example.mobileproject5.fragments.list

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.example.mobileproject5.R
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem


class ListFragment : Fragment(), LocationListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        requestGPS()
        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        val map1=view.findViewById<MapView>(R.id.map1)
        map1.controller.setZoom(14.0)
        map1.controller.setCenter(GeoPoint(37.13, -8.01))
        val items = ItemizedIconOverlay(context, arrayListOf<OverlayItem>(), markerGestureListener)
        val fernhurst = OverlayItem("Fernhurst", "Village in West Sussex", GeoPoint(51.05, -0.72))
        val blackdown = OverlayItem("Blackdown", "highest point in West Sussex", GeoPoint(51.0581, -0.6897))
        items.addItem(fernhurst)
        items.addItem(blackdown)
        map1.overlays.add(items)


        return view
    }

    val markerGestureListener = object:ItemizedIconOverlay.OnItemGestureListener<OverlayItem>
    {
        override fun onItemLongPress(i: Int, item:OverlayItem ) : Boolean
        {
            Toast.makeText(context, item.snippet, Toast.LENGTH_SHORT).show()
            return true
        }

        override fun onItemSingleTapUp(i: Int, item:OverlayItem): Boolean
        {
            Toast.makeText(context, item.snippet, Toast.LENGTH_SHORT).show()
            return true
        }
    }

    override fun onLocationChanged(newLoc: Location) {
        val lat: TextView = requireView().findViewById(R.id.lat)
        lat.text = "${newLoc.latitude}"
        val lon: TextView = requireView().findViewById(R.id.lon)
        lon.text = "${newLoc.longitude}"
        Toast.makeText (context, "Location=${newLoc.latitude},${newLoc.longitude}", Toast.LENGTH_LONG).show()
        newLoc?.apply {
            val map1 = requireView().findViewById<MapView>(R.id.map1)
            map1.controller.setCenter(GeoPoint(latitude, longitude))
            map1.controller.setZoom(18.0);
            addMarker("My Location")
        }
    }

    fun addMarker(Name: String){
        val et1 = requireView().findViewById<TextView>(R.id.lat)
        val et2 = requireView().findViewById<TextView>(R.id.lon)
        val numb1 = et1.text.toString().toDouble()
        val numb2 = et2.text.toString().toDouble()
        val map1 = requireView().findViewById<MapView>(R.id.map1)
        val item2 = ItemizedIconOverlay(context, arrayListOf<OverlayItem>(), markerGestureListener)
        val CurrentLocation = OverlayItem("Current Location", Name,(GeoPoint(numb1, numb2)))
        item2.addItem(CurrentLocation)
        map1.overlays.add(item2)
    }

    override fun onProviderDisabled(provider: String) {
        Toast.makeText (context, "Provider disabled", Toast.LENGTH_LONG).show()
    }

    override fun onProviderEnabled(provider: String) {
        Toast.makeText (context, "Provider enabled", Toast.LENGTH_LONG).show()
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //open the file
                    requestGPS()
                } else {
                    // user has denied file read permission so inform them that the app will not be able to read files
                    AlertDialog.Builder(requireActivity().applicationContext)
                        .setPositiveButton("OK", null) // add an OK button with an optional event handler
                        .setMessage("You need to allow access location in settings for this app to work") // set the message
                        .show() // show the dialog
                }
            }
        }
    }

    private fun requestGPS() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //get our Location Manager abd start receiving updates
            val mgr: LocationManager = getActivity()?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Distance (third argument) is float.
            // In Kotlin we must explicitly use "f" to specify it's a float
            mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,1f,this)
        } else {
            //request permission from the user
            ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
    }


}