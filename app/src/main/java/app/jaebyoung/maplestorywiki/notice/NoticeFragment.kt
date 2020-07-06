package app.jaebyoung.maplestorywiki.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.jaebyoung.maplestorywiki.R

// TODO : 공식 홈페이지 모바일 크롤링
class NoticeFragment : Fragment() {

    private lateinit var noticeViewModel: NoticeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        noticeViewModel =
                ViewModelProviders.of(this).get(NoticeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notice, container, false)
        val textView: TextView = root.findViewById(R.id.text_notice)
        noticeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
