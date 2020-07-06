package app.jaebyoung.maplestorywiki

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.jaebyoung.maplestorywiki.home.list.HomeListData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var jopData = arrayListOf<HomeListData>()
    private var searchData = arrayListOf<SearchResult>()
    private lateinit var resultAdapter: RecyclerView.Adapter<SearchResultAdapter.Holder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        firebaseFirestore = FirebaseFirestore.getInstance()

        loadData()
        searchResultView()
        val toolbar: Toolbar = findViewById(R.id.search_toolbar)
        setSupportActionBar(toolbar)

        cancel_search_button.setOnClickListener {
            finish()
        }

        search_window_button.setOnClickListener {
            if (searchData.size == 1) {
                val intent = intent
                intent.putExtra("search_result", searchData[0].jopName)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        search_log_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    search_log_view.visibility = View.GONE
                    search_result_view.visibility = View.GONE
                    search_log_switch_off.visibility = View.VISIBLE
                    search_log_off_text.text = resources.getString(R.string.jops_log_nothing)
                }
                false -> {
                    search_log_view.visibility = View.GONE
                    search_result_view.visibility = View.GONE
                    search_log_switch_off.visibility = View.VISIBLE
                    search_log_off_text.text = resources.getString(R.string.jops_log_switch_off)
                }
            }
        }

        search_content_area.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (search_log_switch.visibility == View.VISIBLE) {
                    search_log_switch.visibility = View.INVISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val target = s.toString()

                searchData.clear()

                if (target.length == 0) {
                    search_log_view.visibility = View.GONE
                    search_result_view.visibility = View.GONE
                    search_log_switch_off.visibility = View.VISIBLE
                } else {
                    for (data in jopData) {
                        if (data.jopName.contains(target)) {
                            if (search_result_view.visibility == View.GONE) {
                                search_log_view.visibility = View.GONE
                                search_result_view.visibility = View.VISIBLE
                                search_log_switch_off.visibility = View.GONE
                            }
                            searchData.add(SearchResult(data.jopName, data.jopType, data.jopGroup))
                        }
                    }
                    if (searchData.isNotEmpty()) {
                        resultAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (search_log_switch.visibility == View.INVISIBLE) {
            search_log_switch.visibility = View.VISIBLE
        }
    }

    private fun searchResultView() {
        val resultView = findViewById<RecyclerView>(R.id.search_result_recyclerview)
        resultAdapter = SearchResultAdapter(this, searchData)

        resultView.adapter = resultAdapter
        resultView.layoutManager = LinearLayoutManager(this)
        resultView.setHasFixedSize(false)

        (resultAdapter as SearchResultAdapter).setOnItemClickListener(object: SearchResultAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val intent = getIntent()
                intent.putExtra("search_result", searchData[position].jopName)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })
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
}
