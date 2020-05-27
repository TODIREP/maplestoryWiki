package app.jaebyoung.maplestorywiki.home

import android.content.Intent
import android.os.Bundle
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

        tempData()

        fab = view.findViewById(R.id.view_type)
        fabList = view.findViewById(R.id.view_list_layer)
        fabSlot = view.findViewById(R.id.view_slot_layer)
        fabCard = view.findViewById(R.id.view_card_layer)

        fab.setOnClickListener(this)
        fabList.setOnClickListener(this)
        fabSlot.setOnClickListener(this)
        fabCard.setOnClickListener(this)
        view.home_search_button.setOnClickListener(this)

        makeList(view)
        makeFilter(view)
        return view
    }

    private fun makeFilter(view: View) {
        val filter: Spinner = view.findViewById(R.id.home_filter_list)
        val filterList = resources.getStringArray(R.array.home_filter)
        val filterAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, filterList)
        filter.adapter = filterAdapter

        filter.prompt = "분류 선택"
// TODO : 스피너 디자인
    }
// TODO : 상세 페이지 구성
    private fun makeList(view: View) {
        recyclerView = view.findViewById(R.id.home_items_recycler)
        homeAdapter = HomeListAdapter(requireContext(), homeList)

        recyclerView.adapter = homeAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }

    private fun tempData() {
        homeList.add(HomeListData("temp", "팬텀", "영웅"))
        homeList.add(HomeListData("temp", "에반", "영웅"))
        homeList.add(HomeListData("temp", "아란", "영웅"))
        homeList.add(HomeListData("temp", "메르세데스", "영웅"))
        homeList.add(HomeListData("temp", "루미너스", "영웅"))
        homeList.add(HomeListData("temp", "은월", "영웅"))
        homeList.add(HomeListData("temp", "소울마스터", "시그너스"))
        homeList.add(HomeListData("temp", "플레임위자드", "시그너스"))
        homeList.add(HomeListData("temp", "윈드브레이커", "시그너스"))
        homeList.add(HomeListData("temp", "스트라이커", "시그너스"))
        homeList.add(HomeListData("temp", "나이트워커", "시그너스"))
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
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    homeAdapter.notifyDataSetChanged()
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
                    recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    homeAdapter.notifyDataSetChanged()
                }
            }
            R.id.home_search_button -> {
                startActivityForResult(Intent(context, SearchActivity::class.java), homelistsearch)
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
