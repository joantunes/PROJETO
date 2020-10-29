package estg.ipvc.projeto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import estg.ipvc.projeto.R
import estg.ipvc.projeto.entities.Title

class TitleAdapter internal constructor(
context: Context
) : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var titles = emptyList<Title>() // Cached copy of words

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView : TextView = itemView.findViewById(R.id.title)
        val noteItemView : TextView = itemView.findViewById(R.id.notes)
        val dateItemView : TextView = itemView.findViewById(R.id.date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerline, parent, false)
        return TitleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val current = titles[position]

        holder.noteItemView.text=current.notes.toString()
        holder.dateItemView.text=current.date.toString()
        holder.titleItemView.text = current.title.toString()
    }

    internal fun setTitles(titles: List<Title>) {
        this.titles = titles
        notifyDataSetChanged()
    }

    override fun getItemCount() = titles.size
}

