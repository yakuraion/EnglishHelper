package pro.yakuraion.englishhelper

import android.app.Application
import pro.yakuraion.englishhelper.di.AppComponent
import timber.log.Timber

class App : Application() {

    lateinit var appComponent: AppComponent

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
}
