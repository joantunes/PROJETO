package estg.ipvc.projeto

import android.content.Context
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
    private  var id: Int=0
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
                    val x: OutputPost = response.body()!!
                    id=x.id
                    val intent = Intent(this@Login, MapsActivity::class.java)
                    Toast.makeText(this@Login, "Login Correto", Toast.LENGTH_SHORT).show()
                    startActivity(intent)

                    var token = getSharedPreferences("user", Context.MODE_PRIVATE)  //
                    var token2 = getSharedPreferences("id", Context.MODE_PRIVATE)  //
                    var editor = token.edit()
                    var editor2 = token2.edit()
                    editor.putString("user_atual",user)
                    editor2.putInt("id_atual",id)


                    editor.commit()
                    editor2.commit()



                }

            }

            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@Login, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onStart() {
        super.onStart()
        var token = getSharedPreferences("user", Context.MODE_PRIVATE)
        if(token.getString("user_atual"," ") != " ") {


            val intent = Intent(this@Login, MapsActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

    }
}