package app.jaebyoung.maplestorywiki.home.subpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.home.subpage.skills.SkillListAdapter
import app.jaebyoung.maplestorywiki.home.subpage.skills.SkillListData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_sub_view_skills.*
import kotlinx.android.synthetic.main.fragment_sub_view_skills.view.*

class SubViewSkills : Fragment(), View.OnClickListener {
    private var currentSkill: Int = 0
    private lateinit var skillsView: RecyclerView
    private lateinit var skillsAdapter: RecyclerView.Adapter<SkillListAdapter.Holder>
    private var skillList = arrayListOf<SkillListData>()

    private var skillList0 = arrayListOf<SkillListData>()
    private var skillList1 = arrayListOf<SkillListData>()
    private var skillList2 = arrayListOf<SkillListData>()
    private var skillList3 = arrayListOf<SkillListData>()
    private var skillList4 = arrayListOf<SkillListData>()
    private var skillList5 = arrayListOf<SkillListData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sub_view_skills, container, false)

        loadData()
//        tempData()
        makeSkillList(view)
        view.skills_level_0.setOnClickListener(this)
        view.skills_level_1.setOnClickListener(this)
        view.skills_level_2.setOnClickListener(this)
        view.skills_level_3.setOnClickListener(this)
        view.skills_level_4.setOnClickListener(this)
        view.skills_level_5.setOnClickListener(this)

        view.update_button.setOnClickListener(this)
        view.download_button.setOnClickListener(this)

        return view
    }

    private fun loadData() {
        val jopName = "팬텀"
        val storage = FirebaseFirestore.getInstance()

        storage.collection("캐릭터").document(jopName).collection("0차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    skillList0.add(
                        SkillListData(
                            data.get("skillIconImage").toString(),
                            data.get("skillName").toString(),
                            data.get("maxLevel").toString(),
                            data.get("skillContent").toString(),
                            data.get("skillEffect").toString(),
                            data.get("skillClass").toString()
                        )
                    )
                }
            }
    }

    private fun tempData() {
        skillList.add(SkillListData("1", "에르다 노바", "30", "2", "3", "5차"))
        skillList.add(SkillListData("1", "메이플 용사", "30", "2", "3", "4차"))
        skillList.add(SkillListData("1", "홀리 심볼", "30", "2", "3", "3차"))
        skillList.add(SkillListData("1", "로즈 카르트 피날레", "30", "2", "3", "4차"))
        skillList.add(SkillListData("1", "블레스", "30", "2", "3", "2차"))
        skillList.add(SkillListData("1", "더블 점프", "30", "2", "3", "1차"))
        skillList.add(SkillListData("1", "텔레포트", "30", "2", "3", "0차"))

        for (item in skillList) {
            when (item.skillClass) {
                "0차" -> {
                    skillList0.add(item)
                }
                "1차" -> {
                    skillList1.add(item)
                }
                "2차" -> {
                    skillList2.add(item)
                }
                "3차" -> {
                    skillList3.add(item)
                }
                "4차" -> {
                    skillList4.add(item)
                }
                "5차" -> {
                    skillList5.add(item)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        var targetList = arrayListOf<SkillListData>()
        when (id) {
            R.id.update_button -> {
                updateClassThing()
            }
            R.id.download_button -> {
                downLoad()
            }
            R.id.skills_level_0 -> {
                if (currentSkill != 0) {
                    currentSkill = 0
                    resetColors()
                    skills_level_0.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
                    skills_level_0_text.setTextColor(resources.getColor(R.color.colorSecondary))
                } else {
                    skillsAdapter.notifyDataSetChanged()
                }
                targetList = skillList0
            }
            R.id.skills_level_1 -> {
                if (currentSkill != 1) {
                    currentSkill = 1
                    resetColors()
                    skills_level_1.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
                    skills_level_1_text.setTextColor(resources.getColor(R.color.colorSecondary))
                }
                targetList = skillList1
            }
            R.id.skills_level_2 -> {
                if (currentSkill != 2) {
                    currentSkill = 2
                    resetColors()
                    skills_level_2.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
                    skills_level_2_text.setTextColor(resources.getColor(R.color.colorSecondary))
                }
                targetList = skillList2
            }
            R.id.skills_level_3 -> {
                if (currentSkill != 3) {
                    currentSkill = 3
                    resetColors()
                    skills_level_3.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
                    skills_level_3_text.setTextColor(resources.getColor(R.color.colorSecondary))
                }
                targetList = skillList3
            }
            R.id.skills_level_4 -> {
                if (currentSkill != 4) {
                    currentSkill = 4
                    resetColors()
                    skills_level_4.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
                    skills_level_4_text.setTextColor(resources.getColor(R.color.colorSecondary))
                }
                targetList = skillList4
            }
            R.id.skills_level_5 -> {
                if (currentSkill != 5) {
                    currentSkill = 5
                    resetColors()
                    skills_level_5.setBackgroundColor(resources.getColor(R.color.colorPrimaryLight))
                    skills_level_5_text.setTextColor(resources.getColor(R.color.colorSecondary))
                }
                targetList = skillList5
            }
        }
        skillList.clear()
        for (item in targetList) {
            skillList.add(item)
        }
        skillsAdapter.notifyDataSetChanged()
    }

    private fun resetColors() {
        skills_level_0.setBackgroundColor(resources.getColor(R.color.xnaud))
        skills_level_1.setBackgroundColor(resources.getColor(R.color.xnaud))
        skills_level_2.setBackgroundColor(resources.getColor(R.color.xnaud))
        skills_level_3.setBackgroundColor(resources.getColor(R.color.xnaud))
        skills_level_4.setBackgroundColor(resources.getColor(R.color.xnaud))
        skills_level_5.setBackgroundColor(resources.getColor(R.color.xnaud))
        skills_level_0_text.setTextColor(resources.getColor(R.color.colorPrimary))
        skills_level_1_text.setTextColor(resources.getColor(R.color.colorPrimary))
        skills_level_2_text.setTextColor(resources.getColor(R.color.colorPrimary))
        skills_level_3_text.setTextColor(resources.getColor(R.color.colorPrimary))
        skills_level_4_text.setTextColor(resources.getColor(R.color.colorPrimary))
        skills_level_5_text.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun makeSkillList(view: View) {
        skillsView = view.findViewById(R.id.skills_item_list_view)
        skillsAdapter = SkillListAdapter(requireContext(), skillList)

        skillsView.adapter = skillsAdapter
        skillsView.layoutManager = LinearLayoutManager(requireContext())
        skillsView.setHasFixedSize(false)
    }

    private fun updateClassThing() {
        val firestorePath = "gs://maplestory-wiki.appspot.com"
        val jopName = "팬텀"
        val storage = FirebaseFirestore.getInstance()
        val tempList = arrayListOf<SkillListData>()
        tempList.add(
            SkillListData(
                "1",
                "리턴 오브 팬텀",
                "1",
                "팬텀의 전용 비행선인 크리스탈 가든으로 귀환한다.",
                "MP 30 소비, 사용 시 크리스탈 가든으로 귀환",
                "0차"
            )
        )
        tempList.add(
            SkillListData(
                "1",
                "데들리 인스팅트",
                "2",
                "뛰어난 통찰력을 통해 적의 치명적인 약점을 찾아내는 본성을 가지고 있다.\n" +
                        "월드내에 보유한 캐릭터 중 하나에게 이 스킬을 전수해 줄 수 있다.",
                "크리티컬 확률 15% 증가",
                "0차"
            )
        )
        tempList.add(
            SkillListData(
                "1",
                "팬텀 슈라우드",
                "1",
                "카드의 장막으로 모습을 가린 뒤에, 먼 거리로 순간 이동할 수 있다.",
                "MP 30 소비, 사용 시 방향키 입력으로 순간이동 가능\n" +
                        "(갈 수 있는 방향으로만 화살표 표시)\n" +
                        "방향키 입력 횟수 제한 : 3번, 스킬 종료 후 이동 1회마다 재사용 대기시간 1.50초 적용",
                "0차"
            )
        )
        tempList.add(
            SkillListData(
                "1",
                "하이 덱스터러티",
                "1",
                "팬텀은 뛰어난 통찰력과 다양한 무기를 다룰 수 있는 손재주를 가지고 있다.",
                "민첩성 40 증가, 회피율 20% 증가, 손재주 20레벨, 통찰력 20레벨",
                "0차"
            )
        )
        tempList.add(
            SkillListData(
                "1",
                "스틸 스킬",
                "1",
                "주변의 모험가들이 가진 스킬을 훔친다. 특정 스킬들은 훔칠 수 없고 비숍의 힐은 재사용 대기시간이 증가한다.",
                "MP 30 소비, 사용 시 스틸 모드로 전환.\n" +
                        "마우스 커서를 이동하여 주변 모험가 캐릭터를 선택",
                "0차"
            )
        )
        tempList.add(
            SkillListData(
                "1",
                "저지먼트",
                "1",
                "카드 스택이 가득 찼을 때, 스택을 모두 소비하면서 하나의 카드를 뽑는다. 뽑힌 카드에 따라 부가적인 효과를 받을 수 있으며, 추가 카드 공격이 이루어진다.",
                "MP 60 소비, 사용 시 전방의 적에게 10번 느와르 카르트 공격을 하고 30초 동안 다음 효과중에 하나를 받음." +
                        "포츈 카드: 크리티컬 확률 20% 증가" +
                        "미스포츈 카드 : 아이템 드롭률 10% 증가" +
                        "인듀어런스 카드 : 상태이상 내성/속성 내성이 각각 20, 20% 증가" +
                        "드레인 카드 : 공격 시 최대 HP의 1%만큼 회복",
                "0차"
            )
        )
        tempList.add(
            SkillListData(
                "1",
                "저지먼트 AUTOMANUAL",
                "1",
                "저지먼트 스킬의 발동을 자동(AUTO)모드와 수동(MANUAL)모드로 선택할 수 있다. 더블 클릭하여 사용할 때마다 모드가 전환된다.",
                "자동(AUTO)모드 : 스택이 모두 쌓이면 자동으로 저지먼트 효과를 발동한다.\n" +
                        "수동(MANUAL)모드 : 스택이 모두 쌓이면 직접 저지먼트 스킬을 클릭하여 발동한다.",
                "0차"
            )
        )

        for (target in tempList) {
            val skillClass = "${target.skillClass}스킬"
            val skillIconImage = "${firestorePath}/${jopName}/${skillClass}/${target.skillName}.png"
            val data = hashMapOf(
                "skillIconImage" to skillIconImage,
                "skillName" to target.skillName,
                "maxLevel" to target.maxLevel,
                "skillContent" to target.skillContent,
                "skillEffect" to target.skillEffect,
                "skillClass" to target.skillClass
            )

            storage.collection("캐릭터")
                .document(jopName)
                .collection(skillClass)
                .add(data)
                .addOnSuccessListener {
                    Log.d("테스트", "파일 저장 ${it.id}")
                }.addOnFailureListener {
                    Log.d("테스트", "저장 실패 ${it}")
                }
        }
    }

    private fun downLoad() {
        val jopName = "팬텀"
        val storage = FirebaseFirestore.getInstance()

        storage.collection("캐릭터").document(jopName).collection("0차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("테스트", "${document.data}")
                }
            }
        storage.collection("캐릭터").document(jopName).collection("1차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("테스트", "${document.data}")
                }
            }
        storage.collection("캐릭터").document(jopName).collection("2차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("테스트", "${document.data}")
                }
            }
        storage.collection("캐릭터").document(jopName).collection("3차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("테스트", "${document.data}")
                }
            }
        storage.collection("캐릭터").document(jopName).collection("4차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("테스트", "${document.data}")
                }
            }
        storage.collection("캐릭터").document(jopName).collection("5차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("테스트", "${document.data}")
                }
            }
    }
}