package pro.yakuraion.englishhelper.domain.repositories

import android.net.Uri
import java.io.File

interface WordsSoundsRepository {

    suspend fun downloadSoundForWorld(name: String, soundUri: Uri): File?
}
