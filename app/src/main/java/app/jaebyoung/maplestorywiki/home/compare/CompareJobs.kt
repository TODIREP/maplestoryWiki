package app.jaebyoung.maplestorywiki.home.compare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.jaebyoung.maplestorywiki.R
import app.jaebyoung.maplestorywiki.SearchActivity
import kotlinx.android.synthetic.main.activity_compare_jobs.*

// TODO : SearchForCompare 완성 후 수정하기
class CompareJobs : AppCompatActivity(), View.OnClickListener {
    private var isjobA: Boolean = false
    private var isjobB: Boolean = false
    private val INSERT_job_A = 8120
    private val INSERT_job_B = 8121
    private lateinit var jobA: String
    private lateinit var jobB: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_jobs)

        val toolbar: Toolbar = findViewById(R.id.compare_jobs_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_cancel_back_button_white_36dp)

        bottom_button_layer.isEnabled = false
        bottom_button_layer.setOnClickListener(this)
        insert_job_a.setOnClickListener(this)
        insert_job_b.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v!!.id

        when (id) {
            R.id.bottom_button_layer -> {
                val intent = Intent(this, CompareJobContent::class.java)
                intent.putExtra("jobA", jobA)
                intent.putExtra("jobB", jobB)
                startActivity(intent)
            }
            R.id.insert_job_a -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivityForResult(intent, INSERT_job_A)
            }
            R.id.insert_job_b -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivityForResult(intent, INSERT_job_B)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        insert_job_a.text = ""
        insert_job_b.text = ""
        bottom_button_layer.isEnabled = false
        bottom_button_layer.setBackgroundColor(resources.getColor(R.color.grayColor))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                INSERT_job_A -> {
                    if (!isjobA) {
                        isjobA = true
                    }
                    if (isjobA && isjobB) {
                        bottom_button_layer.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                        bottom_button_layer.isEnabled = true
                    }
                    if (data != null) {
                        jobA = data.getStringExtra("search_result")!!
                        insert_job_a.text = jobA
                    }
                }
                INSERT_job_B -> {
                    if (!isjobB) {
                        isjobB = true
                    }
                    if (isjobA && isjobB) {
                        bottom_button_layer.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                        bottom_button_layer.isEnabled = true
                    }
                    if (data != null) {
                        jobB = data.getStringExtra("search_result")!!
                        insert_job_b.text = jobB
                    }
                }
            }
        }
    }
}