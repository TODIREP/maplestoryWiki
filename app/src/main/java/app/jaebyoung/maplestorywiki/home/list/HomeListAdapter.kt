package app.jaebyoung.maplestorywiki.home.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.jaebyoung.maplestorywiki.R
import com.bumptech.glide.Glide

class HomeListAdapter(private val context: Context, private val data: ArrayList<HomeListData>): RecyclerView.Adapter<HomeListAdapter.Holder>() {
    private var mViewType: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layout = when(mViewType) {
            0 -> R.layout.home_recycler_item_list_type
            2 -> R.layout.home_recycler_item_card_type
            else -> R.layout.home_recycler_item_list_type
        }
        val view = LayoutInflater.from(context).inflate(layout, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    inner class Holder (itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.portrait_image)
        private val name: TextView = itemView.findViewById(R.id.jop_title_name)
        private val type: TextView = itemView.findViewById(R.id.jop_type_name)

        fun bind(data: HomeListData) {
            Glide.with(context).load(data.portrait).into(image)
//            image.setImageResource(R.drawable.portrait_mercedes)
            name.text = data.jopName
            type.text = data.jopType
        }
    }
}