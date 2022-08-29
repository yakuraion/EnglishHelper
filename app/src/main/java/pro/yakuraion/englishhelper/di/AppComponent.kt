package pro.yakuraion.englishhelper.di

import dagger.BindsInstance
import dagger.Component
import pro.yakuraion.englishhelper.data.AppDatabase
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun appDatabase(database: AppDatabase): Builder
    }
}
