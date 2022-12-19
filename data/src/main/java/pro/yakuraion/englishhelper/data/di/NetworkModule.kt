package pro.yakuraion.englishhelper.data.di

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Singleton
    @Provides
    fun provideRequestQueue(context: Context): RequestQueue {
        return Volley.newRequestQueue(context)
    }
}
