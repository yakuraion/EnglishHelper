package pro.yakuraion.englishhelper

import android.app.Application
import pro.yakuraion.englishhelper.di.AppComponent
import pro.yakuraion.englishhelper.domain.di.InteractorsProvider
import pro.yakuraion.englishhelper.domain.di.InteractorsProviderHolder

class App : Application(), InteractorsProviderHolder {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent.create(this)
    }

    override fun getInteractorsProvider(): InteractorsProvider {
        return appComponent
    }
}
