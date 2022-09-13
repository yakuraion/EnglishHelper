package pro.yakuraion.englishhelper.data.preferences

import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject

internal class Preferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var learningDay: Int
        get() = sharedPreferences.getInt(LEARNING_DAY_KEY, 0)
        set(value) {
            sharedPreferences.edit().putInt(LEARNING_DAY_KEY, value).apply()
        }

    var lastLearningDate: Calendar
        get() {
            val millis = sharedPreferences.getLong(LAST_LEARNING_DATE_KEY, 0)
            return Calendar.getInstance().apply { timeInMillis = millis }
        }
        set(value) {
            val millis = value.timeInMillis
            sharedPreferences.edit().putLong(LAST_LEARNING_DATE_KEY, millis).apply()
        }

    companion object {

        private const val LEARNING_DAY_KEY = "LEARNING_DAY"
        private const val LAST_LEARNING_DATE_KEY = "LAST_LEARNING_DATE"
    }
}
