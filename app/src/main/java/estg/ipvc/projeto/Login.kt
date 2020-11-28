package estg.ipvc.projeto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import estg.ipvc.projeto.api.EndPoints
import estg.ipvc.projeto.api.OutputPost
import estg.ipvc.projeto.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonRegister.setOnClickListener()
        {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun login(view: View) {

        val user= user.text.toString().trim()
        val pass= password.text.toString().trim()
        val request=ServiceBuilder.buildService(EndPoints::class.java)
        val call= request.postIN(user,pass)

        call.enqueue(object : retrofit2.Callback<OutputPost> {
            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
                if (response.body()?.success == false) {
                    val x: OutputPost = response.body()!!
                    Toast.makeText(
                            this@Login,
                            "Login Incorreto. User ou Password incorretos.",
                            Toast.LENGTH_SHORT
                    ).show()


                } else {
                    val intent = Intent(this@Login, MapsActivity::class.java)
                    Toast.makeText(this@Login, "Login Correto", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }

            }

            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@Login, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}