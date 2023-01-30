package pro.yakuraion.englishhelper.domain.utils

import java.util.*
import javax.inject.Inject

class LearningDatesUtils @Inject constructor(
    private val datesUtils: DatesUtils
) {

    fun getIsLearningDatesTheSame(first: Calendar, second: Calendar): Boolean {
        return datesUtils.getIsDatesTheSame(first.withOffset(), second.withOffset())
    }

    companion object {

        // We assume that days start at 4 AM
        private const val DATE_OFFSET_HOURS = -4

        private fun Calendar.withOffset(): Calendar {
            return (this.clone() as Calendar).apply { add(Calendar.HOUR, DATE_OFFSET_HOURS) }
        }
    }
}
