package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.domain.repositories.DatabaseUpdateRepository
import javax.inject.Inject

internal class UpdateDatabaseAfterMigrationsUseCaseImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val databaseUpdateRepository: DatabaseUpdateRepository
) : UpdateDatabaseAfterMigrationsUseCase {

    override fun getIsNeedToUpdateDatabase(): Boolean {
        return databaseUpdateRepository.getIsNeedToUpdateDatabase()
    }

    override suspend fun updateDatabase() {
        withContext(dispatchers.ioDispatcher) {
            databaseUpdateRepository.updateDatabase()
        }
    }
}
