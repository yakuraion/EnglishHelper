package pro.yakuraion.englishhelper.commontestsui

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule

open class ComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    protected fun getString(@StringRes stringRes: Int): String {
        return composeTestRule.activity.getString(stringRes)
    }
}
