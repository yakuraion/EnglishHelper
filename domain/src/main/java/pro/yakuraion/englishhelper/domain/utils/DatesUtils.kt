package pro.yakuraion.englishhelper.domain.utils

import java.util.*
import javax.inject.Inject

class DatesUtils @Inject constructor() {

    fun getCurrentDate(): Calendar {
        return Calendar.getInstance()
    }

    fun getIsDatesTheSame(first: Calendar, second: Calendar): Boolean {
        return first.get(Calendar.DAY_OF_YEAR) == second.get(Calendar.DAY_OF_YEAR) &&
            first.get(Calendar.YEAR) == second.get(Calendar.YEAR)
    }
}
