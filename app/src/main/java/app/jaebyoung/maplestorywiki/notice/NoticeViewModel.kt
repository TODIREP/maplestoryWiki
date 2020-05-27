package app.jaebyoung.maplestorywiki.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoticeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "공지사항 페이지입니다."
    }
    val text: LiveData<String> = _text
}