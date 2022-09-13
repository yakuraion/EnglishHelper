package pro.yakuraion.englishhelper.data.siteparsers.wooordhunt

import android.content.Context
import android.net.Uri
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class WooordhuntParser @Inject constructor(context: Context) {

    private val queue = Volley.newRequestQueue(context)

    suspend fun getWord(name: String): WooordhuntWord {
        return suspendCoroutine { continuation ->
            requestWordHtml(
                name,
                onResult = { html ->
                    val soundUri = getSoundUriFromHtml(html)
                    val word = WooordhuntWord(soundUri = soundUri)
                    continuation.resume(word)
                },
                onError = { continuation.resumeWithException(it) }
            )
        }
    }

    private fun requestWordHtml(name: String, onResult: (String) -> Unit, onError: (VolleyError) -> Unit) {
        val url = "$BASE_WORD_URL/$name"
        val request = StringRequest(
            url,
            { result -> onResult.invoke(result.orEmpty()) },
            { error -> onError.invoke(error) }
        )
        queue.add(request)
    }

    private fun getSoundUriFromHtml(html: String): Uri? {
        return US_SOUND_REGEX.find(html)
            ?.value
            ?.trim('"')
            ?.let { Uri.parse("$BASE_URL$it") }
    }

    companion object {

        private const val BASE_URL = "https://wooordhunt.ru"
        private const val BASE_WORD_URL = "$BASE_URL/word"

        private val US_SOUND_REGEX = "\"[/a-z]*/us/[/a-z]*\\.mp3\"".toRegex()
    }
}
