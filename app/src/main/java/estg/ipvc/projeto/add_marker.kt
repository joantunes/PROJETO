package estg.ipvc.projeto

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import estg.ipvc.projeto.api.EndPoints
import estg.ipvc.projeto.api.OutputPost
import estg.ipvc.projeto.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_add_marker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class add_marker : AppCompatActivity() {
    private var userID: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_marker)

        var latitude= intent.getStringExtra("latitude")
        var longitude= intent.getStringExtra("longitude")
        var token2 = getSharedPreferences("id", Context.MODE_PRIVATE)
        userID=token2.getInt("id_atual", 0)

        findViewById<TextView>(R.id.lat).setText(latitude)         // coloca valor da latitude no campo Lat do XML
        findViewById<TextView>(R.id.lng).setText(longitude)          // coloca valor da latitude no campo Lat do XML
        findViewById<TextView>(R.id.userID).setText(userID.toString())          // coloca valor da latitude no campo Lat do XML


    }

    fun marker(view: View) {
        val descr = descr.text.toString().trim()
        val latitude = lat.text.toString().trim()
        val longitude = lng.text.toString().trim()

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.postMarker(descr,latitude,longitude,userID)




        call.enqueue(object : Callback<OutputPost> {

            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                if (response.isSuccessful) {
                    val intent = Intent(this@add_marker, MapsActivity::class.java)
                    Toast.makeText(this@add_marker, "Novo Marcador inserido com sucesso", Toast.LENGTH_SHORT).show()
                    intent.putExtra("user_id",userID)
                    startActivity(intent)

                }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@add_marker, "Erro na inserção", Toast.LENGTH_SHORT).show()
            }
        })
    }
}