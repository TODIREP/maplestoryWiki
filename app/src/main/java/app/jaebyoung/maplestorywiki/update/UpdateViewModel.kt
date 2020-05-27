package app.jaebyoung.maplestorywiki.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpdateViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "업데이트 페이지입니다."
    }
    val text: LiveData<String> = _text
}