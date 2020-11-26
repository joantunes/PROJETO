package estg.ipvc.projeto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import estg.ipvc.projeto.R
import estg.ipvc.projeto.entities.Title
import kotlinx.android.synthetic.main.recyclerline.view.*

class   TitleAdapter internal constructor(
        context: Context,
        private val listener: OnItemClickListener



) : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {
    private var titles = emptyList<Title>()



    inner class TitleViewHolder(val view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {

        val TextView1: TextView = itemView.title
        val TextView2: TextView = itemView.notes

        val TextView3: TextView = itemView.date

        init {
            itemView.setOnClickListener{v:View ->
                val position = adapterPosition
                Toast.makeText(itemView.context,"You removed item # ${position+1}", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerline,parent,false)


        return TitleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val current = titles[position]



        holder.TextView1.text =current.title
        holder.TextView2.text=current.notes
        holder.TextView3.text = current.date
        // holder.titleItemView.text=current.id.toString()+ "-" + current.title+"-" + current.note
    }



    internal fun setTitles(titles: List<Title>) {
        this.titles = titles
        notifyDataSetChanged()
    }
    fun getTitleAt(position: Int): Title? {
        return titles[position]
    }

    override fun getItemCount() = titles.size
}

