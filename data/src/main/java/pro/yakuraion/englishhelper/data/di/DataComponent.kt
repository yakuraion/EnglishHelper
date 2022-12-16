package pro.yakuraion.englishhelper.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import pro.yakuraion.englishhelper.domain.di.RepositoriesProvider
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SharedPreferencesModule::class,
        DatabaseModule::class,
        DaosModule::class,
        NetworkModule::class,
        RepositoriesModule::class
    ]
)
interface DataComponent : RepositoriesProvider {

    @Component.Builder
    interface Builder {

        fun build(): DataComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}
