package pro.yakuraion.englishhelper.domain.entities

import android.net.Uri

class WooordhuntWord(
    val name: String,
    val soundUri: Uri,
    val forms: List<String>,
    val examples: List<String>
)
