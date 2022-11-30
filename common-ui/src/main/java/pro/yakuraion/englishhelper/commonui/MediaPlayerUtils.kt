package pro.yakuraion.englishhelper.commonui

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import java.io.File

object MediaPlayerUtils {

    fun playSound(context: Context, file: File) {
        MediaPlayer.create(context, Uri.fromFile(file)).start()
    }
}
