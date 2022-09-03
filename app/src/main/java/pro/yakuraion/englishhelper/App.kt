package pro.yakuraion.englishhelper

import android.app.Application
import pro.yakuraion.englishhelper.di.AppComponent
import pro.yakuraion.englishhelper.di.DaggerAppComponent
import pro.yakuraion.englishhelper.vocabulary.di.VocabularyDependencies
import pro.yakuraion.englishhelper.vocabulary.di.VocabularyDependenciesProvider

class App : Application(), VocabularyDependenciesProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    override fun provideVocabularyDependencies(): VocabularyDependencies {
        return appComponent
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }
}
