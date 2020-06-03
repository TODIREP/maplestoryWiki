package app.jaebyoung.maplestorywiki.home.list

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.home.subpage.CharacterSubpage
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class HomeListAdapter(private val context: Context, private val data: ArrayList<HomeListData>) :
    RecyclerView.Adapter<HomeListAdapter.Holder>() {
    private var mViewType: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layout = when (mViewType) {
            0 -> R.layout.home_recycler_item_list_type
            2 -> R.layout.home_recycler_item_card_type
            else -> R.layout.home_recycler_item_list_type
        }
        val view = LayoutInflater.from(context).inflate(layout, parent, false)

        return Holder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return mViewType
    }

    fun setItemViewType(viewType: Int) {
        this.mViewType = viewType
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val storage = FirebaseStorage.getInstance()
        private val image: ImageView = itemView.findViewById(R.id.portrait_image)
        private val name: TextView = itemView.findViewById(R.id.jop_title_name)
        private val type: TextView = itemView.findViewById(R.id.jop_type_name)

        fun bind(data: HomeListData) {
            val imageRef = storage.getReferenceFromUrl(data.portrait)
            val localFile = File.createTempFile("images", ".png")

            imageRef.getFile(localFile).addOnSuccessListener {
                val tempPath = localFile.absolutePath
                data.setPortraitPath(tempPath)
                val bitmap = BitmapFactory.decodeFile(tempPath)
                image.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.d("테스트", "${data.portrait} 실패맨")
            }
            name.text = data.jopName
            type.text = data.jopType

            itemView.setOnClickListener {
                Log.d("테스트", "${data.jopName} 클릭함")
                val intent = Intent(context, CharacterSubpage::class.java)
                intent.putExtra("data", data)
                context.startActivity(intent)
            }
        }
    }
}