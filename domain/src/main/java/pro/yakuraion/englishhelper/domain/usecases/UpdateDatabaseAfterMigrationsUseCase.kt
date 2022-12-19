package pro.yakuraion.englishhelper.domain.usecases

interface UpdateDatabaseAfterMigrationsUseCase {

    fun getIsNeedToUpdateDatabase(): Boolean

    suspend fun updateDatabase()
}
