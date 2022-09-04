package pro.yakuraion.englishhelper.di.modules

import dagger.Module
import dagger.Provides
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class CoroutinesModule {

    @Singleton
    @Provides
    fun provideDispatchers(): Dispatchers {
        return Dispatchers(
            ioDispatcher = kotlinx.coroutines.Dispatchers.IO,
            computeDispatcher = kotlinx.coroutines.Dispatchers.Default,
            mainDispatcher = kotlinx.coroutines.Dispatchers.Main
        )
    }
}
