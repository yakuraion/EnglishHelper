package pro.yakuraion.englishhelper.domain.interactors

import org.junit.Before
import org.junit.Rule
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.commontests.MainDispatcherRule

abstract class InteractorTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var dispatchers: Dispatchers

    @Before
    open fun setUp() {
        dispatchers = Dispatchers(
            mainDispatcherRule.testDispatcher,
            mainDispatcherRule.testDispatcher,
            mainDispatcherRule.testDispatcher
        )
    }
}
