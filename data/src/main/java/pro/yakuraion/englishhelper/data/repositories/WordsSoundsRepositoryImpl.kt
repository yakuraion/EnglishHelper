package pro.yakuraion.englishhelper.data.repositories

import android.content.Context
import pro.yakuraion.androidcommon.network.downloadFile
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import timber.log.Timber
import java.io.File
import java.net.URL
import javax.inject.Inject

internal class WordsSoundsRepositoryImpl @Inject constructor(
    private val context: Context
) : WordsSoundsRepository {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun downloadSoundForWorld(name: String, soundUri: String): File? {
        val file = getSoundFile(name)
        return try {
            val url = URL(soundUri)
            downloadFile(url, file)
            file
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    private fun getSoundFile(name: String): File {
        return File(context.cacheDir, "sounds/$name.mp3")
    }
}
