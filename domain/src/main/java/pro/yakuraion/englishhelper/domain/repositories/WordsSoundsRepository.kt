package pro.yakuraion.englishhelper.domain.repositories

import java.io.File

interface WordsSoundsRepository {

    suspend fun downloadSoundForWorld(name: String, soundUri: String): File?
}
