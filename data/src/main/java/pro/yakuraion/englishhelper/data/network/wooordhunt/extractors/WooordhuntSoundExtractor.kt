package pro.yakuraion.englishhelper.data.network.wooordhunt.extractors

import android.net.Uri
import pro.yakuraion.englishhelper.data.network.wooordhunt.WooordhuntParser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WooordhuntSoundExtractor @Inject constructor() {

    fun extract(html: String): Uri? {
        return US_SOUND_REGEX.find(html)
            ?.value
            ?.trim('"')
            ?.let { Uri.parse("${WooordhuntParser.BASE_URL}$it") }
    }

    companion object {

        private val US_SOUND_REGEX = "\"[/a-z]*/us/[/a-z]*\\.mp3\"".toRegex()
    }
}
