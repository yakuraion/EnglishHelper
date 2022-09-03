package pro.yakuraion.englishhelper.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import pro.yakuraion.englishhelper.di.modules.DaosModule
import pro.yakuraion.englishhelper.di.modules.DatabaseModule
import pro.yakuraion.englishhelper.di.modules.SharedPreferencesModule
import pro.yakuraion.englishhelper.vocabulary.di.VocabularyDependencies
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, DaosModule::class, SharedPreferencesModule::class])
interface AppComponent : VocabularyDependencies {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}
