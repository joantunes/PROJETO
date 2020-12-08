package estg.ipvc.projeto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import estg.ipvc.projeto.api.EndPoints
import estg.ipvc.projeto.api.OutputPost
import estg.ipvc.projeto.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_add_marker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class remove_marker : AppCompatActivity() {
    private var idProblema:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_marker)
        idProblema=intent.getIntExtra("idProblema",idProblema)
    }

    fun deleteMarker(view: View) {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.delete(idProblema)
        call.enqueue(object : Callback<OutputPost> {

            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                if (response.isSuccessful) {
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

