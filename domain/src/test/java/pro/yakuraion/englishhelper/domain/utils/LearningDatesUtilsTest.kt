package pro.yakuraion.englishhelper.domain.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Parameterized::class)
class LearningDatesUtilsTest(
    private val first: Calendar,
    private val second: Calendar,
    private val isSame: Boolean
) {

    private lateinit var datesUtils: DatesUtils

    private lateinit var learningDatesUtils: LearningDatesUtils

    @Before
    fun setUp() {
        datesUtils = DatesUtils()
        learningDatesUtils = LearningDatesUtils(datesUtils)
    }

    @Test
    fun `get is learning dates the same`() {
        assertEquals(isSame, learningDatesUtils.getIsLearningDatesTheSame(first, second))
    }

    companion object {

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(
                Calendar.getInstance().apply { set(2000, 1, 1, 0, 0) },
                Calendar.getInstance().apply { set(2000, 1, 2, 0, 0) },
                false
            ),
            arrayOf(
                Calendar.getInstance().apply { set(2000, 1, 1, 5, 0) },
                Calendar.getInstance().apply { set(2000, 1, 2, 0, 0) },
                true
            ),
            arrayOf(
                Calendar.getInstance().apply { set(2000, 1, 1, 0, 0) },
                Calendar.getInstance().apply { set(2000, 1, 1, 5, 0) },
                false
            ),
            arrayOf(
                Calendar.getInstance().apply { set(2000, 1, 1, 0, 0) },
                Calendar.getInstance().apply { set(2000, 1, 1, 0, 0) },
                true
            ),
        )
    }
}
