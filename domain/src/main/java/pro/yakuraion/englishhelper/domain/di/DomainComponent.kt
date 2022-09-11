package pro.yakuraion.englishhelper.domain.di

import dagger.BindsInstance
import dagger.Component
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [RepositoriesProvider::class],
    modules = [InteractorsModule::class]
)
interface DomainComponent : InteractorsProvider {

    @Component.Builder
    interface Builder {

        fun build(): DomainComponent

        fun repositoriesProvider(repositoriesProvider: RepositoriesProvider): Builder

        @BindsInstance
        fun dispatchers(dispatchers: Dispatchers): Builder
    }
}
