package com.example.mobileproject5

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity(),LocationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
          return navController.navigateUp() || return super.onSupportNavigateUp()
    }

  /*  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //open the file
                    requestGPS()
                } else {
                    // user has denied file read permission so inform them that the app will not be able to read files
                    AlertDialog.Builder(this)
                        .setPositiveButton("OK", null) // add an OK button with an optional event handler
                        .setMessage("You need to allow access location in settings for this app to work") // set the message
                        .show() // show the dialog
                }
            }
        }
    }

    private fun requestGPS() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //get our Location Manager abd start receiving updates
            val mgr: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Distance (third argument) is float.
            // In Kotlin we must explicitly use "f" to specify it's a float
           mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,1f,this)
        } else {
            //request permission from the user
            ActivityCompat.requestPermissions(this, arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
    }*/

    override fun onLocationChanged(newLoc: Location) {
        Toast.makeText (this, "Location=${newLoc.latitude},${newLoc.longitude}", Toast.LENGTH_LONG).show()

    }

}