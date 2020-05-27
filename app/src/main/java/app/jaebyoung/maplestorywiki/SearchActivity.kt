package app.jaebyoung.maplestorywiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
// TODO : 검색창
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: Toolbar = findViewById(R.id.search_toolbar)
        setSupportActionBar(toolbar)

        cancel_search_button.setOnClickListener {
            finish()
        }

        search_window_button.setOnClickListener {

        }
    }
}
