package app.jaebyoung.maplestorywiki.ranking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import app.jaebyoung.maplestorywiki.R
import kotlinx.android.synthetic.main.fragment_ranking.view.*

class RankingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_ranking, container, false)

        view.text_ranking.text = "랭킹보기 페이지입니다."

        return view
    }

}
