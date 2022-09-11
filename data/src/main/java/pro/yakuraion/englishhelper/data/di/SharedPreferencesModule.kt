package pro.yakuraion.englishhelper.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    companion object {

        private const val PREFERENCES_NAME = "preferences"
    }
}
