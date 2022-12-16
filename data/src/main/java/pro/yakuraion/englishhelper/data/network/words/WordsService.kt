package pro.yakuraion.englishhelper.data.network.words

import com.android.volley.RequestQueue
import pro.yakuraion.englishhelper.data.DataConfiguration
import pro.yakuraion.englishhelper.data.network.volley.GsonRequest
import pro.yakuraion.englishhelper.data.network.words.responses.WordsExamplesResponse
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
internal class WordsService @Inject constructor(
    private val configuration: DataConfiguration,
    private val queue: RequestQueue
) {

    suspend fun getWordsExamples(name: String): WordsExamplesResponse {
        return suspendCoroutine { continuation ->
            val url = getUrl("words/$name/examples")
            val request = GsonRequest(
                url = url,
                clazz = WordsExamplesResponse::class.java,
                headers = getAuthHeaders().toMutableMap(),
                listener = { response -> continuation.resume(response) },
                errorListener = { error -> continuation.resumeWithException(error) }
            )
            queue.add(request)
        }
    }

    private fun getAuthHeaders(): Map<String, String> {
        return mapOf(
            "X-RapidAPI-Host" to configuration.wordsServiceHost,
            "X-RapidAPI-Key" to configuration.wordsServiceKey
        )
    }

    companion object {

        private fun getUrl(path: String): String {
            return "https://wordsapiv1.p.rapidapi.com/$path"
        }
    }
}
