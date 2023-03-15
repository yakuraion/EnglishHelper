package pro.yakuraion.englishhelper.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher

class Dispatchers(
    val ioDispatcher: CoroutineDispatcher,
    val computeDispatcher: CoroutineDispatcher,
    val mainDispatcher: CoroutineDispatcher
)
