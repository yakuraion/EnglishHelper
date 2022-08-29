package pro.yakuraion.englishhelper.di

import dagger.BindsInstance
import dagger.Component
import pro.yakuraion.englishhelper.data.AppDatabase
import pro.yakuraion.englishhelper.vocabulary.di.VocabularyDependencies
import javax.inject.Singleton

@Singleton
@Component(modules = [DaosModule::class])
interface AppComponent : VocabularyDependencies {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun appDatabase(database: AppDatabase): Builder
    }
}
