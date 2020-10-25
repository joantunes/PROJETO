package estg.ipvc.projeto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import estg.ipvc.projeto.R
import estg.ipvc.projeto.dataclasses.Place
import kotlinx.android.synthetic.main.recyclerline.view.*

class LineAdapter(val list: ArrayList<Place>):RecyclerView.Adapter<LineViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {

        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recyclerline, parent, false);
        return LineViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val currentPlace = list[position]

        holder.title.text = currentPlace.title
        holder.notes.text = currentPlace.notes.toString()
        holder.date.text = currentPlace.date.toString()
    }

}

    class LineViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView){
    val title = itemView.title
    var notes = itemView.notes
    var date = itemView.date
}