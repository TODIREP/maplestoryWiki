package app.jaebyoung.maplestorywiki

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchResultAdapter(private val context: Context, private val data: ArrayList<SearchResult>) :
    RecyclerView.Adapter<SearchResultAdapter.Holder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    private lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.search_result_view_item, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.Holder, position: Int) {
        holder.bind(data[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val resultText: TextView = itemView.findViewById(R.id.search_result_text)

        fun bind(data: SearchResult) {
            resultText.text = data.jopName

            itemView.setOnClickListener {
                val pos = adapterPosition

                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(itemView, pos)
                    }
                }
            }
        }
    }
}