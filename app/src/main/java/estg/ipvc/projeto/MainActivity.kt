package estg.ipvc.projeto

import android.os.Bundle
import android.view.View
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

    fun insert(view: View) {
        myList.add(0, Place("Terça-Feira","PARTIU O MOTOR", "24/02/23 XXX"))
        recycler_view.adapter?.notifyDataSetChanged()
    }
}