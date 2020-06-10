package app.jaebyoung.maplestorywiki.home.list

import java.io.Serializable

class HomeListData(
    val portrait: String,
    val jopName: String,
    val jopType: String,
    val jopGroup: String,
    val jopLevel: String
) : Serializable {
    private var portraitPath: String? = null

    fun setPortraitPath(imgPath: String) {
        this.portraitPath = imgPath
    }

    fun getPortraitPath(): String? {
        return portraitPath
    }
}