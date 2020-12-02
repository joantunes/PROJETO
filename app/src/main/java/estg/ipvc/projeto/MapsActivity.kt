package estg.ipvc.projeto

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import estg.ipvc.projeto.api.EndPoints
import estg.ipvc.projeto.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var problems: List<problems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

///////////////////////////// call the service and add markers ///////////////////////////////////////////////////
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getProblems()
        var position: LatLng

        call.enqueue(object : Callback<List<problems>> {
            override fun onResponse(call: Call<List<problems>>, response: Response<List<problems>>) {
                if (response.isSuccessful) {     // Em caso de sucesso
                    problems = response.body()!! // igualar a lista de problemas
                    for (problem in problems) {
                        position = LatLng(problem.lat.toDouble(), //latlng precisa de recerber um double da longitude e latitude
                                problem.lng.toDouble())
                        mMap.addMarker(MarkerOptions().position(position).title(problem.descr + " - " + problem.city))

                    }
                }
            }

            override fun onFailure(call: Call<List<problems>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


    }
}