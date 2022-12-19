package pro.yakuraion.englishhelper.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DatabaseInfoPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var lastDatabaseVersion: Int
        get() = sharedPreferences.getInt(DATABASE_LAST_DATABASE_VERSION, 1)
        set(value) {
            sharedPreferences.edit().putInt(DATABASE_LAST_DATABASE_VERSION, value).apply()
        }

    companion object {

        private const val DATABASE_LAST_DATABASE_VERSION = "DATABASE_LAST_DATABASE_VERSION"
    }
}
