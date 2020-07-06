package app.jaebyoung.maplestorywiki.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.jaebyoung.maplestorywiki.R

// TODO: 공식 홈페이지 크롤링하기
class UpdateFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_update, container, false)
        val textView: TextView = root.findViewById(R.id.text_update)
        return root
    }
}
