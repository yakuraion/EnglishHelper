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
        get() {
            return if (!sharedPreferences.contains(DATABASE_LAST_DATABASE_VERSION)) {
                val databaseVersion = database.openHelper.readableDatabase.version
                sharedPreferences.edit().putInt(DATABASE_LAST_DATABASE_VERSION, databaseVersion).apply()
                databaseVersion
            } else {
                sharedPreferences.getInt(DATABASE_LAST_DATABASE_VERSION, 0)
            }
        }
        set(value) {
            sharedPreferences.edit().putInt(DATABASE_LAST_DATABASE_VERSION, value).apply()
        }

    var updatedTo_4: Boolean
        get() = sharedPreferences.getBoolean(DATABASE_UPDATED_TO_4, false)
        set(value) {
            sharedPreferences.edit().putBoolean(DATABASE_UPDATED_TO_4, value).apply()
        }

    companion object {

        private const val DATABASE_LAST_DATABASE_VERSION = "DATABASE_LAST_DATABASE_VERSION"

        private const val DATABASE_UPDATED_TO_4 = "DATABASE_UPDATED_TO_4"
    }
}
