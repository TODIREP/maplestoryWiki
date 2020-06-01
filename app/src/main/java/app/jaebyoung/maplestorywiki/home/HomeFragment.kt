package app.jaebyoung.maplestorywiki.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.SearchActivity
import app.jaebyoung.maplestorywiki.home.list.HomeListAdapter
import app.jaebyoung.maplestorywiki.home.list.HomeListData
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

    // 0 : 리스트, 1: 그리드, 2: 카드
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeAdapter: RecyclerView.Adapter<HomeListAdapter.Holder>
    private var homeList = arrayListOf<HomeListData>()

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
//        tempData()
        loadData()

        fab = view.findViewById(R.id.view_type)
        fabList = view.findViewById(R.id.view_list_layer)
        fabSlot = view.findViewById(R.id.view_slot_layer)
        fabCard = view.findViewById(R.id.view_card_layer)

        fab.setOnClickListener(this)
        fabList.setOnClickListener(this)
        fabSlot.setOnClickListener(this)
        fabCard.setOnClickListener(this)
        view.home_search_button.setOnClickListener(this)
        view.do_not_touch_this.setOnClickListener(this)

        makeList(view)
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
    }

    private fun makeList(view: View) {
        recyclerView = view.findViewById(R.id.home_items_recycler)
        homeAdapter = HomeListAdapter(requireContext(), homeList)

        recyclerView.adapter = homeAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }

    private fun tempData() {
        homeList.add(HomeListData("1", "소울마스터", "시그너스", "전사", "275"))
        homeList.add(HomeListData("1", "아란", "영웅", "전사", "275"))
        homeList.add(HomeListData("1", "히어로", "모험가", "전사", "275"))
        homeList.add(HomeListData("1", "팔라딘", "모험가", "전사", "275"))
        homeList.add(HomeListData("1", "다크나이트", "모험가", "전사", "275"))

        homeList.add(HomeListData("1", "에반", "영웅", "마법사", "275"))
        homeList.add(HomeListData("1", "플레임위자드", "시그너스", "마법사", "275"))
        homeList.add(HomeListData("1", "루미너스", "영웅", "마법사", "275"))
        homeList.add(HomeListData("1", "아크메이지(불,독)", "모험가", "마법사", "275"))
        homeList.add(HomeListData("1", "아크메이지(썬,콜)", "모험가", "마법사", "275"))
        homeList.add(HomeListData("1", "비숍", "모험가", "마법사", "275"))

        homeList.add(HomeListData("1", "메르세데스", "영웅", "궁수", "275"))
        homeList.add(HomeListData("1", "윈드브레이커", "시그너스", "궁수", "275"))
        homeList.add(HomeListData("1", "보우마스터", "모험가", "궁수", "275"))
        homeList.add(HomeListData("1", "신궁", "모험가", "궁수", "275"))
        homeList.add(HomeListData("1", "패스파인더", "모험가", "궁수", "275"))

        homeList.add(HomeListData("1", "팬텀", "영웅", "도적", "275"))
        homeList.add(HomeListData("1", "나이트워커", "시그너스", "도적", "275"))
        homeList.add(HomeListData("1", "나이트로드", "모험가", "도적", "275"))
        homeList.add(HomeListData("1", "섀도어", "모험가", "도적", "275"))
        homeList.add(HomeListData("1", "듀얼블레이드", "모험가", "도적", "275"))

        homeList.add(HomeListData("1", "은월", "영웅", "해적", "275"))
        homeList.add(HomeListData("1", "스트라이커", "시그너스", "해적", "275"))
        homeList.add(HomeListData("1", "캡틴", "모험가", "해적", "275"))
        homeList.add(HomeListData("1", "바이퍼", "모험가", "해적", "275"))
        homeList.add(HomeListData("1", "캐논슈터", "모험가", "해적", "275"))

        for (target in homeList) {
            val stringImage = "${firestorePath}/${target.jopName} 초상화.png"

            val data = hashMapOf(
                "jop_portrait" to stringImage,
                "jop_name" to target.jopName,
                "jop_type" to target.jopType,
                "jop_group" to target.jopGroup,
                "jop_level" to target.jopLevel
            )

            firebaseFirestore.collection("캐릭터 미리보기")
                .add(data)
                .addOnSuccessListener {
                    Log.d("테스트", "파일 저장 ${it.id}")
                }.addOnFailureListener {
                    Log.d("테스트", "저장 실패 ${it}")
                }
        }
    }

    private fun loadData() {
        firebaseFirestore.collection("캐릭터 미리보기")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    Log.d("테스트", "${data}")
                    val portrait = data.get("jop_portrait").toString()
                    val jopName = data.get("jop_name").toString()
                    val jopType = data.get("jop_type").toString()
                    val jopGroup = data.get("jop_group").toString()
                    val jopLevel = data.get("jop_level").toString()

                    homeList.add(HomeListData(portrait, jopName, jopType, jopGroup, jopLevel))
                }
            }.addOnFailureListener {
                Log.d("테스트", "에러발생 ${it}")
            }
    }

    // TODO : 리사이클러뷰 뷰타입 동적 변경
    // TODO : 그리드 뷰 레이아웃
    override fun onClick(v: View?) {
        val id = v!!.id

        when (id) {
            R.id.view_type -> {
                floatingButton()
            }

            R.id.view_list_layer -> {
                floatingButton()
                if (currentLayout != 0) {
/*
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    homeAdapter.notifyDataSetChanged()
*/
                }
            }

            R.id.view_slot_layer -> {
                floatingButton()
                if (currentLayout != 1) {

                }
            }

            R.id.view_card_layer -> {
                floatingButton()
                if (currentLayout != 2) {
/*
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    homeAdapter.notifyDataSetChanged()
*/
                }
            }
            R.id.home_search_button -> {
                startActivityForResult(Intent(context, SearchActivity::class.java), homelistsearch)
            }
            R.id.do_not_touch_this -> {
                homeAdapter.notifyDataSetChanged()
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
