package app.jaebyoung.maplestorywiki.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.SearchActivity
import app.jaebyoung.maplestorywiki.home.compare.CompareJobs
import app.jaebyoung.maplestorywiki.home.list.HomeListAdapter
import app.jaebyoung.maplestorywiki.home.list.HomeListData
import app.jaebyoung.maplestorywiki.home.subpage.CharacterSubpage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(), View.OnClickListener {
    private val homelistsearch: Int = 10000
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation
    private var isFabOpen: Boolean = false
    private lateinit var fab: FloatingActionButton
    private lateinit var fabList: FloatingActionButton
    private lateinit var fabSlot: FloatingActionButton
    private lateinit var fabCard: FloatingActionButton

    private var currentLayout: Int = 0

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeAdapter: RecyclerView.Adapter<HomeListAdapter.Holder>
    private var homeList = arrayListOf<HomeListData>()
    private var saveList = arrayListOf<HomeListData>()
    private var currentFilter: Int = 0

    private val firestorePath: String = "gs://maplestory-wiki.appspot.com"
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        firebaseFirestore = FirebaseFirestore.getInstance()

        loadData()
        makeList(view)

        fab = view.findViewById(R.id.view_type)
        fabList = view.findViewById(R.id.view_list_layer)
        fabSlot = view.findViewById(R.id.view_slot_layer)
        fabCard = view.findViewById(R.id.view_card_layer)

        fab.setOnClickListener(this)
        fabList.setOnClickListener(this)
        fabSlot.setOnClickListener(this)
        fabCard.setOnClickListener(this)
        view.home_search_button.setOnClickListener(this)
        view.home_compare_jobs.setOnClickListener(this)

        makeFilter(view)

        return view
    }

    private fun makeFilter(view: View) {
        val filter: Spinner = view.findViewById(R.id.home_filter_list)
        val filterList = resources.getStringArray(R.array.home_filter)
        val filterAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, filterList)
        filter.adapter = filterAdapter
        filter.prompt = "분류 선택"

        filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (currentFilter != position) {
                    val keyGroup = arrayOf("전체보기", "전사", "마법사", "궁수", "도적", "해적")
                    currentFilter = position
                    homeList.clear()
                    when (position) {
                        0 -> {
                            for (item in saveList) {
                                homeList.add(item)
                            }
                        }
                        else -> {
                            val keyValue = keyGroup[position]
                            for (item in saveList) {
                                if (item.jopGroup == keyValue) {
                                    homeList.add(item)
                                }
                            }
                        }
                    }
                    homeAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun makeList(view: View) {
        recyclerView = view.findViewById(R.id.home_items_recycler)
        homeAdapter = HomeListAdapter(requireContext(), 0, homeList)

        recyclerView.adapter = homeAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(false)

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

                    homeList.add(HomeListData(portrait, jopName, jopType, jopGroup, jopLevel))
                    saveList.add(HomeListData(portrait, jopName, jopType, jopGroup, jopLevel))
                }
                homeAdapter.notifyDataSetChanged()
            }.addOnFailureListener {
                Log.d("테스트", "에러발생 ${it}")
            }
    }

    override fun onClick(v: View?) {
        val id = v!!.id

        when (id) {
            R.id.view_type -> {
                floatingButton()
            }

            R.id.view_list_layer -> {
                floatingButton()
                if (currentLayout != 0) {
                    homeAdapter = HomeListAdapter(requireContext(), 0, homeList)

                    recyclerView.adapter = homeAdapter
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recyclerView.setHasFixedSize(false)
                    currentLayout = 0
                }
            }

            R.id.view_slot_layer -> {
                floatingButton()
                if (currentLayout != 1) {
                    homeAdapter = HomeListAdapter(requireContext(), 1, homeList)

                    recyclerView.adapter = homeAdapter
                    recyclerView.layoutManager =
                        GridLayoutManager(requireContext(), 2)
                    recyclerView.setHasFixedSize(false)
                    currentLayout = 1
                }
            }

            R.id.view_card_layer -> {
                floatingButton()
                if (currentLayout != 2) {
                    homeAdapter = HomeListAdapter(requireContext(), 2, homeList)

                    recyclerView.adapter = homeAdapter
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    recyclerView.setHasFixedSize(false)
                    currentLayout = 2
                }
            }

            R.id.home_search_button -> {
                startActivityForResult(Intent(context, SearchActivity::class.java), homelistsearch)
            }

            R.id.home_compare_jobs -> {
                val intent = Intent(requireContext(), CompareJobs::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                homelistsearch -> {
                    if (data != null) {
                        val search_jop = data.getStringExtra("search_result")
                        Log.d("테스트", "${search_jop}을(를) 검색합니다.")
                        for (target in saveList) {
                            if (target.jopName == search_jop) {
                                val intent = Intent(context, CharacterSubpage::class.java)
                                intent.putExtra("data", target)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun floatingButton() {
        when (isFabOpen) {
            true -> {
                fab.setImageResource(R.drawable.ic_visibility_black_24dp)
                fabList.startAnimation(fabClose)
                fabSlot.startAnimation(fabClose)
                fabCard.startAnimation(fabClose)
                fabList.isClickable = false
                fabSlot.isClickable = false
                fabCard.isClickable = false
                isFabOpen = false
            }
            false -> {
                fab.setImageResource(R.drawable.ic_clear_black_24dp)
                fabList.startAnimation(fabOpen)
                fabSlot.startAnimation(fabOpen)
                fabCard.startAnimation(fabOpen)
                fabList.isClickable = true
                fabSlot.isClickable = true
                fabCard.isClickable = true
                isFabOpen = true
            }
        }
    }
}