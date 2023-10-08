package pl.vometix.savchenkohackathon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val data: List<ItemData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var onItemClickListener: MyAdapter.OnItemClickListener? = null

    fun setOnItemClickListener(listener: MyAdapter.OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view:View, position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemofrecycle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        val currentItem = data[position]

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        private val itemText: TextView = itemView.findViewById(R.id.itemText)
        private val documentText:TextView = itemView.findViewById(R.id.documentid)
        fun bind(itemData: ItemData) {
            itemImage.setImageResource(itemData.imageResource)
            itemText.text = itemData.text
            documentText.text = itemData.documntid
        }
    }

    data class ItemData(val imageResource: Int, val text: String, var documntid:String)
}
