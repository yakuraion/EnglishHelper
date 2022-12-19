package pro.yakuraion.englishhelper.data.repositories

import pro.yakuraion.englishhelper.data.database.AppDatabase
import pro.yakuraion.englishhelper.data.preferences.DatabaseInfoPreferences
import pro.yakuraion.englishhelper.data.repositories.dbupdaters.DatabaseUpdater
import pro.yakuraion.englishhelper.domain.repositories.DatabaseUpdateRepository
import javax.inject.Inject

internal class DatabaseUpdateRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val databaseInfoPreferences: DatabaseInfoPreferences,
    private val databaseUpdaters: @JvmSuppressWildcards Map<Int, DatabaseUpdater>
) : DatabaseUpdateRepository {

    override fun getIsNeedToUpdateDatabase(): Boolean {
        return databaseUpdaters.any { it.key >= databaseInfoPreferences.lastDatabaseVersion }
    }

    override suspend fun updateDatabase() {
        val actualDatabaseVersion = database.openHelper.readableDatabase.version
        while (databaseInfoPreferences.lastDatabaseVersion != actualDatabaseVersion) {
            databaseUpdaters[databaseInfoPreferences.lastDatabaseVersion]?.update()
            databaseInfoPreferences.lastDatabaseVersion += 1
        }
    }
}
