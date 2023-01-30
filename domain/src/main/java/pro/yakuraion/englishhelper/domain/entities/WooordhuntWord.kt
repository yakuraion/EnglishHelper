package pro.yakuraion.englishhelper.domain.entities

data class WooordhuntWord(
    val name: String,
    val html: String,
    val soundUri: String,
    val wordForms: List<String>,
    val examples: List<Example>
) {

    data class Example(
        val sentence: String,
        val ruTranslate: String
    )
}
