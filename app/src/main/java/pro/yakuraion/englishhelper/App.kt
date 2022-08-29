package pro.yakuraion.englishhelper

import android.app.Application
import androidx.room.Room
import pro.yakuraion.englishhelper.data.AppDatabase
import pro.yakuraion.englishhelper.di.AppComponent
import pro.yakuraion.englishhelper.di.DaggerAppComponent

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun createAppDatabase(): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME).build()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
            .appDatabase(createAppDatabase())
            .build()
    }

    companion object {

        private const val DATABASE_NAME = "database"
    }
}
