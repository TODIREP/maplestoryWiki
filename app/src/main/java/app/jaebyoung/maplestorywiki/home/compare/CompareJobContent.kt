package app.jaebyoung.maplestorywiki.home.compare

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import app.jaebyoung.maplestorywiki.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.content_compare_data.*
import java.io.File

class CompareJobContent : AppCompatActivity() {
    private lateinit var jobA: String
    private lateinit var jobB: String
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_compare_data)
        val toolbar = findViewById<Toolbar>(R.id.content_compare_toolbar)
        setSupportActionBar(toolbar)

        val intent = intent
        jobA = intent.getStringExtra("jobA")!!
        jobB = intent.getStringExtra("jobB")!!
        toolbar.title = "${jobA} vs ${jobB}"

        setView()
    }

    private fun setView() {
        db.collection("캐릭터 미리보기").whereEqualTo("jop_name", jobA)
            .get().addOnSuccessListener {
                for (result in it) {
                    val imageA = findViewById<ImageView>(R.id.job_image_a)
                    val imageRef = storage.getReferenceFromUrl(
                        result.data.get("jop_portrait").toString()
                    )
                    val localFile = File.createTempFile("images", ".png")
                    imageRef.getFile(localFile).addOnSuccessListener {
                        val tempPath = localFile.absolutePath
                        val bitmap = BitmapFactory.decodeFile(tempPath)
                        imageA.setImageBitmap(bitmap)
                    }.addOnFailureListener {
                        Log.d("테스트", "로딩 실패")
                    }
                }

                db.collection("캐릭터")
                    .document("윈드브레이커")
                    .get()
                    .addOnSuccessListener { result ->
                        if (result != null) {
                            val data = result.data!!

                            compare_basic_status_a.text =
                                "${data.get("mainStat")}"
                            compare_basic_mweapon_a.text =
                                "${data.get("mainWeapon")}"
                            compare_basic_sweapon_a.text =
                                "${data.get("subWeapon")}"
                            compare_stat_attack_a.text = "${data.get("공격력")}/5"
                            compare_stat_movement_a.text = "${data.get("기동성")}/5"
                            compare_stat_difficult_a.text = "${data.get("난이도")}/5"
                            compare_stat_defense_a.text = "${data.get("방어력")}/5"
                        }
                    }
            }.addOnFailureListener {
                Log.d("테스트", "${it}")
            }

        db.collection("캐릭터 미리보기").whereEqualTo("jop_name", jobB)
            .get().addOnSuccessListener {
                for (result in it) {
                    val imageB = findViewById<ImageView>(R.id.job_image_b)
                    val imageRef = storage.getReferenceFromUrl(
                        result.data.get("jop_portrait").toString()
                    )
                    val localFile = File.createTempFile("images", ".png")
                    imageRef.getFile(localFile).addOnSuccessListener {
                        val tempPath = localFile.absolutePath
                        val bitmap = BitmapFactory.decodeFile(tempPath)
                        imageB.setImageBitmap(bitmap)
                    }.addOnFailureListener {
                        Log.d("테스트", "실패")
                    }
                }

                db.collection("캐릭터")
                    .document("팬텀")
                    .get()
                    .addOnSuccessListener { result ->
                        if (result != null) {
                            val data = result.data!!

                            compare_basic_status_b.text =
                                "${data.get("mainStat")}"
                            compare_basic_mweapon_b.text =
                                "${data.get("mainWeapon")}"
                            compare_basic_sweapon_b.text =
                                "${data.get("subWeapon")}"
                            compare_stat_attack_b.text = "${data.get("공격력")}/5"
                            compare_stat_movement_b.text = "${data.get("기동성")}/5"
                            compare_stat_difficult_b.text = "${data.get("난이도")}/5"
                            compare_stat_defense_b.text = "${data.get("방어력")}/5"
                        }
                    }
            }.addOnFailureListener {
                Log.d("테스트", "${it}")
            }
    }
}