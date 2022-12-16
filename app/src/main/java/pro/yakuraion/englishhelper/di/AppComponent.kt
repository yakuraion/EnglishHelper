package pro.yakuraion.englishhelper.di

import android.content.Context
import dagger.Component
import pro.yakuraion.englishhelper.BuildConfig
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.data.DataConfiguration
import pro.yakuraion.englishhelper.data.di.DaggerDataComponent
import pro.yakuraion.englishhelper.data.di.DataComponent
import pro.yakuraion.englishhelper.domain.di.DaggerDomainComponent
import pro.yakuraion.englishhelper.domain.di.DomainComponent
import pro.yakuraion.englishhelper.domain.di.RepositoriesProvider
import pro.yakuraion.englishhelper.domain.di.UseCasesProvider
import javax.inject.Singleton

@Singleton
@Component(dependencies = [UseCasesProvider::class])
interface AppComponent : UseCasesProvider {

    companion object {

        fun create(context: Context): AppComponent {
            val dispatchers = getDispatchers()
            val dataComponent = getDataComponent(context)
            val domainComponent = getDomainComponent(dataComponent, dispatchers)
            return DaggerAppComponent.builder()
                .useCasesProvider(domainComponent)
                .build()
        }

        private fun getDispatchers(): Dispatchers {
            return Dispatchers(
                ioDispatcher = kotlinx.coroutines.Dispatchers.IO,
                computeDispatcher = kotlinx.coroutines.Dispatchers.Default,
                mainDispatcher = kotlinx.coroutines.Dispatchers.Main
            )
        }

        private fun getDataComponent(context: Context): DataComponent {
            return DaggerDataComponent.builder()
                .context(context)
                .configuration(getDataConfiguration())
                .build()
        }

        private fun getDataConfiguration(): DataConfiguration {
            return DataConfiguration(
                wordsServiceHost = BuildConfig.NETWORK_WORDS_HOST,
                wordsServiceKey = BuildConfig.NETWORK_WORDS_KEY
            )
        }

        private fun getDomainComponent(
            repositoriesProvider: RepositoriesProvider,
            dispatchers: Dispatchers
        ): DomainComponent {
            return DaggerDomainComponent.builder()
                .repositoriesProvider(repositoriesProvider)
                .dispatchers(dispatchers)
                .build()
        }
    }
}
