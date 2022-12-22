package pro.yakuraion.englishhelper.data.network.wooordhunt.extractors

import pro.yakuraion.englishhelper.data.network.wooordhunt.WooordhuntHtmlDownloader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class WooordhuntSoundExtractor @Inject constructor() {

    fun extract(html: String): String? {
        return US_SOUND_REGEX.find(html)
            ?.value
            ?.trim('"')
            ?.let { "${WooordhuntHtmlDownloader.BASE_URL}$it" }
    }

    companion object {

        private val US_SOUND_REGEX = "\"[/a-z]*/us/[/a-z]*\\.mp3\"".toRegex()
    }
}
