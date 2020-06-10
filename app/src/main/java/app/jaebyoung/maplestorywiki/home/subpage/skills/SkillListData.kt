package app.jaebyoung.maplestorywiki.home.subpage.skills

import java.io.Serializable

class SkillListData(
    val skillIcon: String,
    val skillName: String,
    val maxLevel: String,
    val skillContent: String,
    val skillEffect: String,
    val skillClass: String
) : Serializable {
    private var skillIconPath: String? = null

    fun setIconPath(path: String) {
        this.skillIconPath = path
    }

    fun getIconPath(): String? {
        return skillIconPath
    }
}