package pro.yakuraion.englishhelper.data.repositories.dbupdaters

internal interface DatabaseUpdater {

    suspend fun update()
}
