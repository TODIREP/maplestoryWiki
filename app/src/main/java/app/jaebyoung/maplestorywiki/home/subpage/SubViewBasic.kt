package app.jaebyoung.maplestorywiki.home.subpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.jaebyoung.maplestorywiki.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_sub_view_basic.*

// TODO : 직업별 상세 정보 데이터 구축.
class SubViewBasic : Fragment() {
    private val storage = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sub_view_basic, container, false)

        storage.collection("캐릭터")
            .document("윈드브레이커")
            .get()
            .addOnSuccessListener { result ->
                if (result != null) {
                    val data = result.data!!

                    stat_main_weapon.text = "주무기 : ${data.get("mainWeapon")}"
                    stat_sub_weapon.text = "보조무기 : ${data.get("subWeapon")}"
                    stat_main_status.text = "주스텟 : ${data.get("mainStat")}"

                    power_attack.text = "공격력 : ${data.get("공격력")}/5"
                    power_sheild.text = "방어력 : ${data.get("방어력")}/5"
                    power_movement.text = "기동성 : ${data.get("기동성")}/5"
                    power_difficult.text = "난이도 : ${data.get("난이도")}/5"
                }
            }

        return view

    }
}