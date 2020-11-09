package estg.ipvc.projeto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import estg.ipvc.projeto.adapter.TitleAdapter
import estg.ipvc.projeto.entities.Title
import estg.ipvc.projeto.viewModel.TitleViewModel

class MainActivity : AppCompatActivity(), TitleAdapter.OnItemClickListener{

    private lateinit var text1: EditText
    private lateinit var titleViewModel: TitleViewModel
    private val newWordActivityRequestCode = 1
    private val adapter = TitleAdapter(this,this)
    private var titles = emptyList<Title>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.setHasFixedSize(true)
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

            val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        // Row is swiped from recycler view
                        // remove it from adapter
                        //adapter.notifyItemRemoved(viewHolder.adapterPosition);
                        adapter.getTitleAt(viewHolder.adapterPosition)?.let {
                            titleViewModel.delete(
                                it
                            )
                        }

                    }


                }
            val itemTouchHelperCallback2: ItemTouchHelper.SimpleCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        // Row is swiped from recycler view
                        // remove it from adapter
                        //adapter.notifyItemRemoved(viewHolder.adapterPosition);
                        adapter.getTitleAt(viewHolder.adapterPosition)?.let {
                            val intent = Intent(this@MainActivity,Editar::class.java)
                            titleViewModel.update(
                                it
                            )
                        }

                    }


                }

// attaching the touch helper to recycler view
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
            ItemTouchHelper(itemTouchHelperCallback2).attachToRecyclerView(recyclerView)




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
                val intent = Intent(this@MainActivity, Remover::class.java)
                startActivityForResult(intent, newWordActivityRequestCode)
                text1=findViewById(R.id.title)
                val titulo = text1.text.toString()
                Toast.makeText(this, "Removed $titulo..", Toast.LENGTH_SHORT).show()
              // titleViewModel.deleteByTitle(text1)
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

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
       // val clikedItem =titles[position]
        //clikedItem.s
        //startActivity(Intent(this@TitleAdpater,Remover::class.java))
        //adapter.notifyItemChanged(position)

    }


}