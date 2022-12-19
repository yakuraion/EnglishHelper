package pro.yakuraion.englishhelper.data.network.wooordhunt

import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.jsoup.Jsoup
import pro.yakuraion.englishhelper.data.network.wooordhunt.extractors.WooordhuntExamplesExtractor
import pro.yakuraion.englishhelper.data.network.wooordhunt.extractors.WooordhuntFormsExtractor
import pro.yakuraion.englishhelper.data.network.wooordhunt.extractors.WooordhuntSoundExtractor
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
internal class WooordhuntParser @Inject constructor(
    private val queue: RequestQueue,
    private val soundExtractor: WooordhuntSoundExtractor,
    private val formsExtractor: WooordhuntFormsExtractor,
    private val examplesExtractor: WooordhuntExamplesExtractor
) {

    suspend fun getWord(name: String): WooordhuntWord? {
        return suspendCoroutine { continuation ->
            requestWordHtml(
                name,
                onResult = { html ->
                    val document = Jsoup.parse(html)

                    val soundUri = soundExtractor.extract(html)
                    val forms = formsExtractor.extract(name, document)
                    val examples = examplesExtractor.extract(document)

                    val word = soundUri?.let {
                        WooordhuntWord(name, soundUri, forms, examples)
                    }
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

    companion object {

        const val BASE_URL = "https://wooordhunt.ru"

        private const val BASE_WORD_URL = "$BASE_URL/word"
    }
}
