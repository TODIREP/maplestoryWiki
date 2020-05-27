package app.jaebyoung.maplestorywiki.home.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import app.jaebyoung.maplestorywiki.R
// TODO : 스피너 커스텀!
class MyHomeSpinner(private var context: Context, private var title: Array<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        when (convertView == null) {
//            true -> {
//                val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//                convertView = inflater.inflate(R.layout.custom_spinner_for_home_filter, parent, false)
//            }
//        }
//
//        return convertView
        return convertView!!
    }

    override fun getItem(position: Int): Any {
        return title[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return title.size
    }
}