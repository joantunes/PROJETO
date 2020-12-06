package estg.ipvc.projeto

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_marker.*

class remove_marker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_marker)


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            R.id.delete -> {
                val intent = Intent(this@remove_marker, MapsActivity::class.java)
                startActivity(intent)
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }

}