package app.jaebyoung.maplestorywiki.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.jaebyoung.maplestorywiki.R
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_event, container, false)

        view.text_event.text = "이벤트 페이지입니다."

        return view
    }
}
