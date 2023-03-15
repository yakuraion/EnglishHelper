package pro.yakuraion.englishhelper.domain.usecases

import org.junit.Before
import org.junit.Rule
import pro.yakuraion.androidcommon.coroutinestests.MainDispatcherRule

abstract class UseCaseTest<T : Any> {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var useCase: T

    abstract fun setUpMocks()

    abstract fun createUseCase(dispatchers: Dispatchers): T

    @Before
    open fun setUp() {
        val dispatchers = Dispatchers(
            mainDispatcherRule.testDispatcher,
            mainDispatcherRule.testDispatcher,
            mainDispatcherRule.testDispatcher
        )
        setUpMocks()
        useCase = createUseCase(dispatchers)
    }
}
