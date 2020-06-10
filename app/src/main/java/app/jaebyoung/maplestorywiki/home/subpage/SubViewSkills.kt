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
        val jopName = "윈드브레이커"
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
                skillList.clear()
                for (item in skillList0) {
                    skillList.add(item)
                }
                skillsAdapter.notifyDataSetChanged()
            }
        storage.collection("캐릭터").document(jopName).collection("1차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    skillList1.add(
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
        storage.collection("캐릭터").document(jopName).collection("2차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    skillList2.add(
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
        storage.collection("캐릭터").document(jopName).collection("3차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    skillList3.add(
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
        storage.collection("캐릭터").document(jopName).collection("4차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    skillList4.add(
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
        storage.collection("캐릭터").document(jopName).collection("5차스킬")
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    skillList5.add(
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

    override fun onClick(v: View?) {
        val id = v!!.id
        var targetList = arrayListOf<SkillListData>()
        when (id) {
            /*R.id.update_button -> {
                updateClassThing()
            }
            R.id.download_button -> {
                downLoad()
            }*/
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

/*    private fun updateClassThing() {
        val firestorePath = "gs://maplestory-wiki.appspot.com"
        val jopName = "윈드브레이커"
        val storage = FirebaseFirestore.getInstance()
        val tempList = arrayListOf<SkillListData>()

        tempList.add(
            SkillListData(
                "1",
                "",
                "",
                "",
                "",
                ""
            )
        )

        for (target in tempList) {
            val skillClass = "${target.skillClass}스킬"
            val skillIconImage = "${firestorePath}/${jopName}/${target.skillName}.png"
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
    }*/
}