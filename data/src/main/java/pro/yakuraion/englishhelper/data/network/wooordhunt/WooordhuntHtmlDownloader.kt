package pro.yakuraion.englishhelper.data.network.wooordhunt

import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
internal class WooordhuntHtmlDownloader @Inject constructor(
    private val queue: RequestQueue,
) {

    suspend fun downloadHtml(word: String): String? {
        return suspendCoroutine { continuation ->
            requestHtml(
                word,
                onResult = { html -> continuation.resume(html) },
                onError = { continuation.resumeWithException(it) }
            )
        }
    }

    private fun requestHtml(name: String, onResult: (String) -> Unit, onError: (VolleyError) -> Unit) {
        val url = "$BASE_WORD_URL/$name"
        val request = StringRequest(
            url,
            { result -> onResult.invoke(result.orEmpty()) },
            { error -> onError.invoke(error) }
        )
        queue.add(request)
    }

    companion object {

        const val BASE_URL = "https://wooordhunt.ru"

        private const val BASE_WORD_URL = "$BASE_URL/word"
    }
}
