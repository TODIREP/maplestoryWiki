package app.jaebyoung.maplestorywiki.home.subpage

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.home.list.HomeListData
import kotlinx.android.synthetic.main.activity_character_subpage.*

// TODO : 상세 페이지 구성

class CharacterSubpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_subpage)
        setSupportActionBar(ch_sub_toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_cancel_back_button_white_36dp)

        val intent = getIntent()
        val data: HomeListData = intent.getSerializableExtra("data") as HomeListData
        val bitmap = BitmapFactory.decodeFile(data.getPortraitPath())

        ch_sub_toolbar_image.setImageBitmap(bitmap)
        ch_sub_toolbar.title = data.jopName

        test_portrait_image.setImageBitmap(bitmap)
        test_jop_title_name.text = data.jopName
        test_jop_type_name.text = data.jopType
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.character_subpage, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            android.R.id.home -> {
                finish()
            }
            R.id.ch_sub_app_settings -> {
                Log.d("테스트", "앱 설정 열기")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}