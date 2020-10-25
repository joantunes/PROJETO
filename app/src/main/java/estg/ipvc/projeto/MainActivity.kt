package estg.ipvc.projeto

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
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

   

   /* override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.create_new -> {
                Toast.makeText(this,"Inserir", Toast.LENGTH_SHORT).show()
                true
            }
            R.id -> {
                Toast.makeText(this,"opcao2", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.opcao3 -> {
                Toast.makeText(this,"opcao3", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.opcao4 -> {
                Toast.makeText(this,"opca4", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }




    }

    */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

}