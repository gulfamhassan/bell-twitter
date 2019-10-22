package com.bell.twitter.assignment.maps

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.maps.maps.MapFragment
import com.bell.twitter.assignment.utils.PermissionValidator
import com.bell.twitter.assignment.utils.PermissionValidator.REQUEST_PERMISSION_CODE

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (PermissionValidator.isLocationPermissionAllowed(this)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.map_container, MapFragment.newInstance())
                .commit()
        } else {
            PermissionValidator.requestPermission(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.map_container, MapFragment.newInstance())
                    .commit()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.message_allow_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        @JvmStatic
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
                .apply {
                }
        }
    }
}