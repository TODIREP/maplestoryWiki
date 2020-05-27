package app.jaebyoung.maplestorywiki.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import app.jaebyoung.maplestorywiki.R
import kotlinx.android.synthetic.main.fragment_community.view.*

class CommunityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_community, container, false)

        view.text_community.text = "커뮤니티 페이지입니다."

        return view
    }
}
