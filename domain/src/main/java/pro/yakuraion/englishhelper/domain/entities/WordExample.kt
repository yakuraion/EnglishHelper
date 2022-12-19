package pro.yakuraion.englishhelper.domain.entities

data class WordExample(
    val sentence: String,
    val missedWord: String
) {

    companion object {

        fun fromFilledSentence(sentence: String, wordForms: List<String>): WordExample? {
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
                val sentenceWithGap = sentence.replace(foundForm, "%s")
                WordExample(sentence = sentenceWithGap, missedWord = foundForm)
            }
        }
    }
}
