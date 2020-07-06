package app.jaebyoung.maplestorywiki.home.compare

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.home.list.HomeListData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search_for_compare.*

class SearchForCompare : AppCompatActivity() {
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var jopData = arrayListOf<HomeListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_compare)
        firebaseFirestore = FirebaseFirestore.getInstance()
        loadData()
        val toolbar: Toolbar = findViewById(R.id.search_jops_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_cancel_back_button_white_36dp)

        search_jops_log_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    search_jops_log_view.visibility = View.GONE
                    if_jops_log_switch_off.visibility = View.VISIBLE
                    log_off_text.text = resources.getString(R.string.jops_log_nothing)
                }
                false -> {
                    search_jops_log_view.visibility = View.GONE
                    if_jops_log_switch_off.visibility = View.VISIBLE
                    log_off_text.text = resources.getString(R.string.jops_log_switch_off)
                }
            }
        }
    }

    private fun loadData() {
        firebaseFirestore.collection("캐릭터 미리보기")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    val portrait = data.get("jop_portrait").toString()
                    val jopName = data.get("jop_name").toString()
                    val jopType = data.get("jop_type").toString()
                    val jopGroup = data.get("jop_group").toString()
                    val jopLevel = data.get("jop_level").toString()

                    jopData.add(HomeListData(portrait, jopName, jopType, jopGroup, jopLevel))
                }
            }.addOnFailureListener {
                Log.d("테스트", "에러발생 ${it}")
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}