package estg.ipvc.projeto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import estg.ipvc.projeto.R
import estg.ipvc.projeto.entities.Title
import kotlinx.android.synthetic.main.recyclerline.view.*

class TitleAdapter internal constructor(
context: Context
) : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var titles = emptyList<Title>() // Cached copy of words

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TextView1 : TextView = itemView.title
        val TextView2 : TextView = itemView.notes
        val TextView3 : TextView = itemView.date
       // val titleItemView: TextView = itemView.findViewById(R.id.textView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerline, parent, false)


        return TitleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val current = titles[position]

        holder.TextView1.text =current.title
        holder.TextView2.text=current.notes
        holder.TextView3.text = current.date
       // holder.titleItemView.text=current.id.toString()+ "-" + current.title+"-" + current.notes

    }

    internal fun setTitles(titles: List<Title>) {
        this.titles = titles
        notifyDataSetChanged()
    }

    override fun getItemCount() = titles.size
}

