package estg.ipvc.projeto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import estg.ipvc.projeto.adapter.TitleAdapter
import estg.ipvc.projeto.entities.Title
import estg.ipvc.projeto.viewModel.TitleViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var titleViewModel: TitleViewModel
    private val newWordActivityRequestCode = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TitleAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        titleViewModel=ViewModelProvider(this).get(TitleViewModel::class.java)
        titleViewModel.allTitles.observe(this, Observer { titles ->
            // Update the cached copy of the words in the adapter.
            titles?.let { adapter.setTitles(it) }
        })

        //VIEW MODEL

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddCity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)

    }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {

            val ptitle = data?.getStringExtra(AddCity.EXTRA_REPLY_TITLE)
            val pnote = data?.getStringExtra(AddCity.EXTRA_REPLY_NOTES)
            val pdate = data?.getStringExtra(AddCity.EXTRA_REPLY_DATE)


            if (ptitle != null && pnote != null && pdate != null) {
                val note = Title(title = ptitle, notes = pnote, date = pdate)
                titleViewModel.insert(note)
            }
        }
        else {
            Toast.makeText(
                applicationContext,
               "Titulo vazio!",
                Toast.LENGTH_LONG).show()
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when(item.itemId) {
            R.id.remove -> {
                titleViewModel.deleteAll()
                Toast.makeText(this, "Removed..", Toast.LENGTH_SHORT).show()

                true
            }
            R.id.edit -> {

                Toast.makeText(this, "Edited..", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.removeX->{
                Toast.makeText(this, "Removed..", Toast.LENGTH_SHORT).show()
                //titleViewModel.delete()
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