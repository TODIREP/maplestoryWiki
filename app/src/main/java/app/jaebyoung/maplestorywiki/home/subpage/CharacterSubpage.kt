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
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_character_subpage.*
import kotlinx.android.synthetic.main.content_character_subpage.*
import java.io.File

// TODO : 상단 앱바 부분 디자인 및 내용 추가
class CharacterSubpage : AppCompatActivity(), View.OnClickListener {
    private var currentView: Int = 0
    private lateinit var jopName: String
    private lateinit var jopType: String
    private var basicFragment: Fragment? = null
    private val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_subpage)
        setSupportActionBar(ch_sub_toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_cancel_back_button_white_36dp)

        val intent = getIntent()
        val data: HomeListData = intent.getSerializableExtra("data") as HomeListData

        val bitmapImage = data.getPortraitPath()
        if (bitmapImage == null) {
            val imageRef = storage.getReferenceFromUrl(data.portrait)
            val localFile = File.createTempFile("images", ".png")

            Glide.with(this).load(R.drawable.orange_mushroom).into(ch_sub_toolbar_image)

            imageRef.getFile(localFile).addOnSuccessListener {
                val tempPath = localFile.absolutePath
                data.setPortraitPath(tempPath)

                val bitmap = BitmapFactory.decodeFile(tempPath)
                ch_sub_toolbar_image.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.d("테스트", "${data.portrait} 실패맨")
            }
        } else {
            val bitmap = BitmapFactory.decodeFile(bitmapImage)
            ch_sub_toolbar_image.setImageBitmap(bitmap)
        }

        jopName = data.jopName
        jopType = data.jopType
        ch_sub_toolbar.title = jopName

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
                val fragment = SubViewBasic()
                val bundle = Bundle()
                bundle.putString("jop_name", jopName)
                bundle.putString("jop_type", jopType)
                fragment.arguments = bundle

                replaceFragment(fragment)
            }
            R.id.sub_view_skills_layout -> {
                if (currentView != 1) {
                    sub_view_basic_text.setTextColor(resources.getColor(R.color.colorPrimary))
                    sub_view_basic_bar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    sub_view_skills_text.setTextColor(resources.getColor(R.color.colorSecondary))
                    sub_view_skills_bar.setBackgroundColor(resources.getColor(R.color.colorSecondary))
                    currentView = 1
                }
                val fragment = SubViewSkills()
                val bundle = Bundle()
                bundle.putString("jop_name", jopName)
                bundle.putString("jop_type", jopType)
                fragment.arguments = bundle

                replaceFragment(fragment)
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
            }
        }
        return super.onOptionsItemSelected(item)
    }
}