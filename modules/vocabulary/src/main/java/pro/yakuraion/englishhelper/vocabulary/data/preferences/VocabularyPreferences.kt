package pro.yakuraion.englishhelper.vocabulary.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class VocabularyPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var learningDay: Int
        get() = sharedPreferences.getInt(LEARNING_DAY_KEY, 0)
        set(value) {
            sharedPreferences.edit().putInt(LEARNING_DAY_KEY, value).apply()
        }

    companion object {

        private const val LEARNING_DAY_KEY = "VOCABULARY_LEARNING_DAY"
    }
}
