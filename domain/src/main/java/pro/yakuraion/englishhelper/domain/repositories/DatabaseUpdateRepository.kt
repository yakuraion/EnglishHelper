package pro.yakuraion.englishhelper.domain.repositories

interface DatabaseUpdateRepository {

    fun getIsNeedToUpdateDatabase(): Boolean

    suspend fun updateDatabase()
}
