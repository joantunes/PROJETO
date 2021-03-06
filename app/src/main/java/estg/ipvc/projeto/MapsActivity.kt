package estg.ipvc.projeto


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import estg.ipvc.projeto.api.EndPoints
import estg.ipvc.projeto.api.OutputPost
import estg.ipvc.projeto.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_maps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsActivity : AppCompatActivity(),OnMapReadyCallback, SensorEventListener {

    private lateinit var mMap: GoogleMap
    private lateinit var problems: List<problems>


    private var userID:Int=0
    private var userIDprob:Int=0

    private lateinit var x: Marker
    // add to implement last known location
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //added to implement location periodic updates
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    //SENSORES
    var sensor : Sensor? = null
    var sensorManager : SensorManager? = null
    private var cnt : Int = 0




    //added to implement distance between two locations
    private var continenteLat: Double = 0.0
    private var continenteLong: Double = 0.0
    //added to implement distance between two locations
    private lateinit var lat: String
    private lateinit var lng: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

///////////////////////////// call the service and add markers ///////////////////////////////////////////////////
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getProblems()
        var position: LatLng
        userID =  intent.getIntExtra("id_user", 0)


        //added to implement distance between two locations
        continenteLat = 41.7043
        continenteLong = -8.8148


        // initialize fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        call.enqueue(object : Callback<List<problems>> {
            override fun onResponse(call: Call<List<problems>>, response: Response<List<problems>>) {
                if (response.isSuccessful) {
                    problems = response.body()!!
                    for (problem in problems) {

                        position = LatLng(problem.lat.toDouble(),
                                problem.lng.toDouble())

                        x = mMap.addMarker(
                                MarkerOptions()
                                        .position(position).title(problem.userID.toString() + " " + problem.descr))
                        x.tag = problem.id


                    }
                }


            }

            override fun onFailure(call: Call<List<problems>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        //added to implement location periodic updates
                locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                lat = loc.latitude.toString()
                lng = loc.longitude.toString()

                // preenche as coordenadas
                findViewById<TextView>(R.id.txtcoordenadas).setText("Lat: " + loc.latitude + " - Long: " + loc.longitude)
                Log.d("** ZE", "new location received - " + loc.latitude + " -" + loc.longitude)

                // distancia
                findViewById<TextView>(R.id.txtdistancia).setText("Distância: " + calculateDistance(
                        lastLocation.latitude, lastLocation.longitude,
                        continenteLat, continenteLong).toString())
            }
        }


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // request creation
        createLocationRequest()
    }
    fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        } else {
// 1
            mMap.isMyLocationEnabled = true
// 2
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
// Got last known location. In some rare situations this can be null.
// 3
                if (location != null) {
                    lastLocation = location
                    Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
       // Toast.makeText(this, cnt.toString(), Toast.LENGTH_SHORT).show()

        setUpMap()
        mMap.setOnMarkerClickListener {
            val intent = Intent(this@MapsActivity, remove_marker::class.java)
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val idProblema = it.tag


            val call = request.postON(idProblema as Int)
            call.enqueue(object : Callback<OutputPost> {

                override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                    if (response.isSuccessful) {
                        val a: OutputPost = response.body()!!
                        userIDprob = a.userID
                        Toast.makeText(this@MapsActivity, "dados:" + userIDprob, Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                    Toast.makeText(this@MapsActivity, "Erro", Toast.LENGTH_SHORT).show()
                }
            })
            if (userIDprob==userID)
            {
                intent.putExtra("idProblema", idProblema)
                startActivity(intent)

            }
            else
            {
                Toast.makeText(this@MapsActivity, "Este marker não é seu", Toast.LENGTH_SHORT).show()
            }



            true
        }
    }


    companion object {
        // add to implement last known location
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1

        //added to implement location periodic updates
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    //added to implement location periodic updates
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        // interval specifies the rate at which your app will like to receive updates.
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("**** ZE", "onPause - removeLocationUpdates")
    }

    public override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
        startLocationUpdates()
        Log.d("**** ZE", "onResume - startLocationUpdates")
    }


    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        // distance in meter
        return results[0]
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            R.id.logout -> {
                var token = getSharedPreferences("user", Context.MODE_PRIVATE)
                var editor = token.edit()
                editor.putString("user_atual", " ")
                editor.commit()
                val intent = Intent(this@MapsActivity, Login::class.java)
                startActivity(intent)
                true
            }
            R.id.add -> {
                val intent = Intent(this@MapsActivity, add_marker::class.java)
                intent.putExtra("latitude", lat)
                intent.putExtra("longitude", lng)
                startActivity(intent)

                true
            }


            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        try {

            if (event!!.values[0] < 30) {


                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = mMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    this, R.raw.mapstyle))
                    if (!success) {
                        Log.e("MapsActivity", "Style parsing failed.")
                    }
                } catch (e: Resources.NotFoundException) {
                    Log.e("MapsActivity", "Can't find style. Error: ", e)
                }



            } else {

                findViewById<FrameLayout>(R.id.map).visibility = View.VISIBLE
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = mMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    this, R.raw.mapstyle2))
                    if (!success) {
                        Log.e("MapsActivity", "Style parsing failed.")
                    }
                } catch (e: Resources.NotFoundException) {
                    Log.e("MapsActivity", "Can't find style. Error: ", e)
                }

            }

        }
        catch (e : Exception)
        {

        }
    }



    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}

