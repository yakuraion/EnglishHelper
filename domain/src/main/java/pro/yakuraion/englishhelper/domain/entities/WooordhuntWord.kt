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
    ) {

        fun toExtraExample(wordForms: List<String>): WordExtra.Example? {
            val sortedForms = wordForms.sortedByDescending { it.length }
            val foundForm = sortedForms.firstOrNull { form ->
                val parts = sentence.split(form, ignoreCase = true)

                // verify that we have only one occurrence of a form
                val isSingleOccurrence = parts.count() == 2

                // we don't know how to work with possessive forms
                val isNotPossessive = parts.all { !it.startsWith('\'') }

                isSingleOccurrence && isNotPossessive
            }

            return foundForm?.let {
                val sentenceWithGap = sentence.replace(foundForm, "%s", ignoreCase = true)
                WordExtra.Example(
                    sentenceWithGap = sentenceWithGap,
                    missedWord = foundForm,
                    ruTranslate = ruTranslate
                )
            }
        }
    }
}
