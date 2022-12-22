@file:Suppress("MagicNumber")

package pro.yakuraion.englishhelper.data.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import pro.yakuraion.englishhelper.data.repositories.dbupdaters.DatabaseUpdater
import pro.yakuraion.englishhelper.data.repositories.dbupdaters.DatabaseUpdaterTo_3

@Suppress("FunctionName")
@Module
internal interface DatabaseUpdatersModule {

    @Binds
    @IntoMap
    @IntKey(3)
    fun binds_3(impl: DatabaseUpdaterTo_3): DatabaseUpdater
}
