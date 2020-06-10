package app.jaebyoung.maplestorywiki.home.subpage.skills

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
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class SkillListAdapter(private val context: Context, private val data: ArrayList<SkillListData>) :
    RecyclerView.Adapter<SkillListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.recycler_item_sub_skills, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val storage = FirebaseStorage.getInstance()
        private val icons: ImageView = itemView.findViewById(R.id.skills_icon_image)
        private val name: TextView = itemView.findViewById(R.id.skills_name)
        private val maxLevel: TextView = itemView.findViewById(R.id.skills_max_level)

        fun bind(data: SkillListData) {
            val imageRef = storage.getReferenceFromUrl(data.skillIcon)
            val iconPath: String? = data.getIconPath()

            if (iconPath == null) {
                val localFile = File.createTempFile("images", ".png")
                val tempSkillIcon =
                    BitmapFactory.decodeResource(context.resources, R.drawable.maple_icon_image)
                icons.setImageBitmap(tempSkillIcon)
                name.text = "스킬 이름 : ???"
                maxLevel.text = "최고 레벨 : ???"

                imageRef.getFile(localFile).addOnSuccessListener {
                    val tempPath = localFile.absolutePath
                    data.setIconPath(tempPath)
                    val bitmap = BitmapFactory.decodeFile(tempPath)
                    icons.setImageBitmap(bitmap)
                    name.text = data.skillName
                    maxLevel.setText("최고 레벨 : ${data.maxLevel}")
                }.addOnFailureListener {
                    Log.d("테스트", "${data.skillIcon} 실패맨")
                }
            } else {
                val bitmap = BitmapFactory.decodeFile(iconPath)
                icons.setImageBitmap(bitmap)
                name.text = data.skillName
                maxLevel.setText("최고 레벨 : ${data.maxLevel}")
            }

            itemView.setOnClickListener {
                val intent = Intent(context, SkillDetailContent::class.java)
                intent.putExtra("data", data)
                context.startActivity(intent)
            }
        }
    }
}