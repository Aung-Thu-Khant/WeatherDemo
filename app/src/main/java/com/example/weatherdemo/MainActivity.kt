package com.example.weatherdemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherdemo.databinding.ActivityMainBinding
import com.example.weatherdemo.model.Weather
import com.example.weatherdemo.network.ApiInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // Check and request location permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if(location != null){
                val latitude = location.latitude
                val longitude = location.longitude
                Toast.makeText(this, "Lat: $latitude, Lon: $longitude", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
            }
        }

        fetchWeatherData()

        setContentView(binding.root)



    }


    fun fetchWeatherData(){
        // Step 1 Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/" ) // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Call getWeather
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val call = apiInterface.getWeather(
            latitude = 52.52, // Replace with actual latitude
            longitude = 13.41, // Replace with actual longitude
            daily = "temperature_2m_max,temperature_2m_min,rain_sum",
            hourly = "temperature_2m,rain",
            current = "temperature_2m,is_day,wind_speed_10m,wind_direction_10m,wind_gusts_10m"
        )

        // success or fail
        call.enqueue(object : retrofit2.Callback<Weather> {
            override fun onResponse(call: retrofit2.Call<Weather>, response: retrofit2.Response<Weather>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    if (weather != null) {
                        // Handle the weather data
                        Toast.makeText(this@MainActivity, "Weather data fetched successfully", Toast.LENGTH_SHORT).show()
                        Log.d("Weather",weather.toString())
                        var todayTemperature = weather.current.temperature_2m.toString()+weather.current_units.temperature_2m.toString()
                        Log.d("Weather", "Current Temperature: $todayTemperature")
                        binding.txtCurrentTemp.text = todayTemperature

                    } else {
                        Toast.makeText(this@MainActivity, "No weather data available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to fetch weather data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<Weather>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d("WeatherError", "Error fetching weather data: ${t.message} , ${t.localizedMessage}")
            }
        })

    }




}