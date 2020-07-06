package app.jaebyoung.maplestorywiki.home.compare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.home.list.HomeListAdapter
import app.jaebyoung.maplestorywiki.home.list.HomeListData
import kotlinx.android.synthetic.main.activity_compare_jobs.*

// TODO : 공사 필요함.
class CompareJobs : AppCompatActivity(), View.OnClickListener {
    //    private lateinit var keyboardManager: InputMethodManager
//    private val db = FirebaseFirestore.getInstance()
//    private val storage = FirebaseStorage.getInstance()
//    private lateinit var jobA: String
//    private lateinit var jobB: String
    private var isjobA: Boolean = false
    private var isjobB: Boolean = false
    private val INSERT_JOP_A = 8120
    private val INSERT_JOP_B = 8121

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_jobs)

        val toolbar: Toolbar = findViewById(R.id.compare_jobs_toolbar)
        setSupportActionBar(toolbar)
//        setInputManager()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_cancel_back_button_white_36dp)

        check_job_btn.setOnClickListener(this)
        bottom_button_layer.setOnClickListener(this)
        insert_job_a.setOnClickListener(this)
        insert_job_b.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v!!.id

        when (id) {
            R.id.check_job_btn -> {
                if (isjobA && isjobB) {
                    bottom_button_layer.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                }
            }
            R.id.bottom_button_layer -> {
                compare_jobs_select.visibility = View.GONE
                compare_data_scrollview.visibility = View.VISIBLE
            }
            R.id.insert_job_a -> {
                val intent = Intent(this, SearchForCompare::class.java)
                startActivityForResult(intent, INSERT_JOP_A)
            }
            R.id.insert_job_b -> {
                val intent = Intent(this, SearchForCompare::class.java)
                startActivityForResult(intent, INSERT_JOP_B)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                INSERT_JOP_A -> {
                    if (!isjobA) {
                        isjobA = true
                    }
                }
                INSERT_JOP_B -> {
                    if (!isjobB) {
                        isjobB = true
                    }
                }
            }
        }
    }

/*
    private fun setInputManager() {
        keyboardManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val inputA: EditText = findViewById(R.id.insert_job_a)
        val inputB: EditText = findViewById(R.id.insert_job_b)

        inputA.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    jobA = insert_job_a.text.toString()

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
                            isjobA = true

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
                            isjobA = false
                            Log.d("테스트", "${it}")
                        }
                    keyboardManager.hideSoftInputFromWindow(inputA.windowToken, 0)
                }
                return true
            }
        })

        inputB.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    jobB = insert_job_b.text.toString()

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
                            isjobB = true

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
                            isjobB = false
                            Log.d("테스트", "${it}")
                        }
                    keyboardManager.hideSoftInputFromWindow(inputB.windowToken, 0)
                }
                return true
            }
        })
    }
*/
}