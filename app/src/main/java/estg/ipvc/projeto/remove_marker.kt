package estg.ipvc.projeto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import estg.ipvc.projeto.api.EndPoints
import estg.ipvc.projeto.api.OutputPost
import estg.ipvc.projeto.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_remove_marker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class remove_marker : AppCompatActivity() {
    private var idProblema:Int=0
    private var userID2:Int=0

    private lateinit var lat2: String
    private lateinit var lng2: String





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_marker)
       idProblema=intent.getIntExtra("idProblema",idProblema)
       /* userID2=intent.getIntExtra("userID2",0)

        lat2=intent.getStringExtra("lat2").toString()
        lng2=intent.getStringExtra("lng2").toString()


        Toast.makeText(this, lat2+lng2, Toast.LENGTH_SHORT).show()

        findViewById<TextView>(R.id.userID2).setText(userID2.toString())         // coloca valor da latitude no campo Lat do XML

        findViewById<TextView>(R.id.lat2).setText(lat2)          // coloca valor da latitude no campo Lat do XML
        findViewById<TextView>(R.id.lng2).setText(lng2)         // coloca valor da latitude no campo Lat do XML

*/


    }

    fun deleteMarker(view: View) {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.delete(idProblema)

        call.enqueue(object : Callback<OutputPost> {

            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                if (response.isSuccessful)
                {
                    val intent = Intent(this@remove_marker, MapsActivity::class.java)
                    Toast.makeText(this@remove_marker, "Marcador removido com sucesso", Toast.LENGTH_SHORT).show()
                    startActivity(intent)

                }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@remove_marker, "Erro na remoção", Toast.LENGTH_SHORT).show()
            }
        })
    }


}

