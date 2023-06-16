package com.example.github

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationRequest
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.databinding.ActivityHomeBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import java.io.IOException
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<UserViewModel>()
    lateinit var locationRequest: com.google.android.gms.location.LocationRequest
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val adapter by lazy {
        UserAdapter{user ->
            Intent(this, DetailActivity::class.java).apply{
                putExtra("username",user.login)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLocationListener()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        viewModel.resultUser.observe(this){
            when(it){
                is ResultSealed.Success<*> -> {
                    adapter.setData(it.data as MutableList<ItemsItem>)
                }
                is ResultSealed.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultSealed.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        viewModel.getUser()
    }

    private fun getLocationListener() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.location.setOnClickListener(){
            checkLocationPermission()
        }
    }

    private fun checkLocationPermission() {
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        {
            //permission is grant
            checkGPS()
        }
        else{
           //permission is denied
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }
    }

    private fun checkGPS() {
        locationRequest = com.google.android.gms.location.LocationRequest.create()
        locationRequest.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        builder.setAlwaysShow(true)

        val resultLocation = LocationServices.getSettingsClient(
            this.applicationContext
        )
            .checkLocationSettings(builder.build())
        resultLocation.addOnCompleteListener { task ->
        try{
            //when gps is on
            val response = task.getResult(
                ApiException::class.java
            )
            getUserLocation()
        }catch(e: ApiException){
            //when gps is off
            e.printStackTrace()
            when(e.statusCode){
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try{
                    //send the request for enable gps
                    val resolveApiException = e as ResolvableApiException
                    resolveApiException.startResolutionForResult(this, 200)

                }catch (sendIntentException : IntentSender.SendIntentException){

                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    //when setting is unavailable
                }
            }
        }
        }
    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location = task.getResult()

            if(location != null){
                try{
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                    //set address in textview
                    val address_line = address!![0].getAddressLine(0)
                    binding.txtLocation.setText(address_line)

                    val address_location = address!![0].getAddressLine(0)

                    openLocation(address_location.toString())

                }catch (e: IOException){

                }
            }
        }
    }

    private fun openLocation(location: String) {
        //open the location in google map
        binding.txtLocation.setOnClickListener() {
            if(binding.txtLocation.text.isEmpty()){
                val uri = Uri.parse("geo:0, 0?q=$location")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent .setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                Intent(this, ProfileActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.faq -> {
                Intent(this, FaqActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.help -> {
                Intent(this, HelpActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.logout -> {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}