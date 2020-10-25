package estg.ipvc.projeto

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import estg.ipvc.projeto.adapter.LineAdapter
import estg.ipvc.projeto.dataclasses.Place
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var myList: ArrayList<Place>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myList = ArrayList<Place>()


        for (i in 0 until 500) {
            myList.add(Place( "Segunda-Feira$i", "NOTA:$i", "23-32-23"))
        }
        recycler_view.adapter = LineAdapter(myList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        //recycler_view.setHasFixedSize(true)


    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when(item.itemId) {
            R.id.create_new-> {
                Toast.makeText(this, "Inserting..", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Inserir::class.java).apply {}
                startActivity(intent)
                true
            }
            R.id.remove -> {
                Toast.makeText(this, "Removing..", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Remover::class.java).apply {}
                startActivity(intent)
                true
            }
            R.id.edit -> {
                Toast.makeText(this, "Editing..", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Editar::class.java).apply {}
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

}