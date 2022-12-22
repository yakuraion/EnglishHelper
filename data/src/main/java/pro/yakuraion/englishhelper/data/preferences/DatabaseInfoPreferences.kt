package pro.yakuraion.englishhelper.data.preferences

import android.content.SharedPreferences
import pro.yakuraion.englishhelper.data.database.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DatabaseInfoPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val database: AppDatabase,
) {

    var lastDatabaseVersion: Int
        get() = sharedPreferences.getInt(DATABASE_LAST_DATABASE_VERSION, database.openHelper.readableDatabase.version)
        set(value) {
            sharedPreferences.edit().putInt(DATABASE_LAST_DATABASE_VERSION, value).apply()
        }

    companion object {

        private const val DATABASE_LAST_DATABASE_VERSION = "DATABASE_LAST_DATABASE_VERSION"
    }
}
