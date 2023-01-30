package pro.yakuraion.englishhelper.domain.fakes

import com.google.gson.Gson
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord

object WooordhuntWords {

    object Existed {

        val WORD by lazy { getWord("word") }

        val THOROUGHLY by lazy { getWord("thoroughly") }
    }

    object NotFound {

        const val TOOWOO = "toowoo"
    }
}

private fun getWord(name: String): WooordhuntWord {
    val fileName = name.lowercase().replace(" ", "_")
    return Unit.javaClass.classLoader
        ?.getResourceAsStream("wooordhunt/$fileName.json")
        ?.bufferedReader()
        ?.readText()
        ?.let { Gson().fromJson(it, WooordhuntWord::class.java) }
        ?: error("Error during wooordhunt word extracting.")
}
