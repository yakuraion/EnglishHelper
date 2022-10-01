package pro.yakuraion.englishhelper

import android.app.Application
import pro.yakuraion.englishhelper.di.AppComponent
import pro.yakuraion.englishhelper.domain.di.UseCasesProvider
import pro.yakuraion.englishhelper.domain.di.UseCasesProviderHolder
import timber.log.Timber

class App : Application(), UseCasesProviderHolder {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        appComponent = AppComponent.create(this)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getUseCasesProvider(): UseCasesProvider {
        return appComponent
    }
}
