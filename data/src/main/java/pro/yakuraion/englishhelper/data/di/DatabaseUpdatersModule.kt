package pro.yakuraion.englishhelper.data.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import pro.yakuraion.englishhelper.data.repositories.dbupdaters.DatabaseUpdater
import pro.yakuraion.englishhelper.data.repositories.dbupdaters.DatabaseUpdaterFrom_1

@Suppress("FunctionName")
@Module
internal interface DatabaseUpdatersModule {

    @Binds
    @IntoMap
    @IntKey(1)
    fun binds_1(impl: DatabaseUpdaterFrom_1): DatabaseUpdater
}
