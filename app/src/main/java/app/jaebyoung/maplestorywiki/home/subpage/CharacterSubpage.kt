package app.jaebyoung.maplestorywiki.home.subpage

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.home.list.HomeListData
import kotlinx.android.synthetic.main.activity_character_subpage.*

class CharacterSubpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_subpage)

        val intent = getIntent()

        val data: HomeListData = intent.getSerializableExtra("data") as HomeListData

        val bitmap = BitmapFactory.decodeFile(data.getPortraitPath())
        test_portrait_image.setImageBitmap(bitmap)
        test_jop_title_name.text = data.jopName
        test_jop_type_name.text = data.jopType
    }
}