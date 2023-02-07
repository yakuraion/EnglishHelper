package pro.yakuraion.englishhelper.commontestsui

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule

open class ComposeBaseRobot(
    protected val composeTestRule: AndroidComposeTestRule<*, *>
) {

    protected fun getString(@StringRes stringRes: Int): String {
        return composeTestRule.activity.getString(stringRes)
    }
}
