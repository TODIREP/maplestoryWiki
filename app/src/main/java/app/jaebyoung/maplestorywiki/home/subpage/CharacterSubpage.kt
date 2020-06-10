package app.jaebyoung.maplestorywiki.home.subpage

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.home.list.HomeListData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_character_subpage.*
import kotlinx.android.synthetic.main.content_character_subpage.*

class CharacterSubpage : AppCompatActivity(), View.OnClickListener {
    private var currentView: Int = 0

    private var basicFragment: Fragment? = null

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

        sub_view_basic_layout.setOnClickListener(this)
        sub_view_skills_layout.setOnClickListener(this)

        basicFragment = SubViewBasic()
        replaceFragment(basicFragment)
    }

    private fun replaceFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.sub_view_fragment_layout, fragment!!)
        fragmentTransaction.commit()
    }

    override fun onClick(v: View?) {
        val id = v!!.id

        when (id) {
            R.id.sub_view_basic_layout -> {
                if (currentView != 0) {
                    sub_view_basic_text.setTextColor(resources.getColor(R.color.colorSecondary))
                    sub_view_basic_bar.setBackgroundColor(resources.getColor(R.color.colorSecondary))
                    sub_view_skills_text.setTextColor(resources.getColor(R.color.colorPrimary))
                    sub_view_skills_bar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    currentView = 0
                }
                replaceFragment(SubViewBasic())
            }
            R.id.sub_view_skills_layout -> {
                if (currentView != 1) {
                    sub_view_basic_text.setTextColor(resources.getColor(R.color.colorPrimary))
                    sub_view_basic_bar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    sub_view_skills_text.setTextColor(resources.getColor(R.color.colorSecondary))
                    sub_view_skills_bar.setBackgroundColor(resources.getColor(R.color.colorSecondary))
                    currentView = 1
                }
                replaceFragment(SubViewSkills())
            }
        }
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

               /* val data = hashMapOf(
                    "스킬이름" to "더블점프"
                )

                val storage = FirebaseFirestore.getInstance()
                storage.collection("캐릭터")
                    .document("스트라이커")
                    .collection("1차스킬")
                    .add(data)
                    .addOnSuccessListener {
                        Log.d("테스트", "파일 저장 ${it.id}")
                    }.addOnFailureListener {
                        Log.d("테스트", "저장 실패 ${it}")
                    }*/
            }
        }
        return super.onOptionsItemSelected(item)
    }
}