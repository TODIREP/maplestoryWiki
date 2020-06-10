package app.jaebyoung.maplestorywiki.home.subpage.skills

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.jaebyoung.maplestorywiki.R
import kotlinx.android.synthetic.main.activity_skill_detail_content.*

class SkillDetailContent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_detail_content)

        val intent = intent
        val data: SkillListData = intent.getSerializableExtra("data") as SkillListData

        val image: String? = data.getIconPath()

        val bitmap = BitmapFactory.decodeFile(image)
        skill_detail_view_icon.setImageBitmap(bitmap)
        skill_detail_view_name.text = data.skillName
        skill_detail_view_max_level.text = "[마스터 레벨 : ${data.maxLevel}]"
        skill_detail_view_content.text = data.skillContent
        skill_detail_view_current_level.text = "[현재 레벨 ${data.maxLevel}]"
        skill_detail_view_effect.text = data.skillEffect
        skill_detail_view_close_button.setOnClickListener {
            finish()
        }
    }
}