package pro.yakuraion.englishhelper.data.repositories

import android.content.Context
import pro.yakuraion.englishhelper.common.downloadFile
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import java.io.File
import javax.inject.Inject

internal class WordsSoundsRepositoryImpl @Inject constructor(
    private val context: Context
) : WordsSoundsRepository {

    override suspend fun downloadSoundForWorld(name: String, soundUri: String): File? {
        val file = getSoundFile(name)
        val result = downloadFile(soundUri, file)
        return if (result) file else null
    }

    private fun getSoundFile(name: String): File {
        return File(context.cacheDir, "sounds/$name.mp3")
    }
}
