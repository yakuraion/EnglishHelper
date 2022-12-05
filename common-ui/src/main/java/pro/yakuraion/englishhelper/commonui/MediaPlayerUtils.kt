package pro.yakuraion.englishhelper.commonui

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

object MediaPlayerUtils {

    fun playSound(context: Context, uriString: String) {
        MediaPlayer.create(context, Uri.parse(uriString)).start()
    }
}
