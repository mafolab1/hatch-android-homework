package co.hatch.application

import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import co.hatch.navigation.NavArguments
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideConnectivityClient(): ConnectivityClient = ConnectivityClient.Factory.create()

	@Provides
	@Singleton
	fun provideNavArguments(): NavArguments = NavArguments()
}
