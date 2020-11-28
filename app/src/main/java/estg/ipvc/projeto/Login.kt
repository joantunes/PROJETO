package estg.ipvc.projeto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonRegister.setOnClickListener()
        {
            val intent= Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}