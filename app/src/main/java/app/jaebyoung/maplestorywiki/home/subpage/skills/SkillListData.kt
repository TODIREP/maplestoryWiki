package app.jaebyoung.maplestorywiki.home.subpage.skills

import java.io.Serializable

class SkillListData(
    val skillIcon: String,
    val skillName: String,
    val maxLevel: String,
    val skillContent: String,
    val skillEffect: String,
    val skillClass: String
) : Serializable