package pro.yakuraion.englishhelper.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import pro.yakuraion.englishhelper.data.database.AppDatabase
import pro.yakuraion.englishhelper.data.database.MIGRATION_1_2
import javax.inject.Singleton

@Module
internal class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    companion object {

        private const val DATABASE_NAME = "database"
    }
}
