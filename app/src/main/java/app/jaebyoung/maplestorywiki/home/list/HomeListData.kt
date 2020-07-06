package app.jaebyoung.maplestorywiki.home.list

import java.io.Serializable

class HomeListData(
    val portrait: String,
    val jobName: String,
    val jobType: String,
    val jobGroup: String,
    val jobLevel: String
) : Serializable {
    private var portraitPath: String? = null

    fun setPortraitPath(imgPath: String) {
        this.portraitPath = imgPath
    }

    fun getPortraitPath(): String? {
        return portraitPath
    }
}