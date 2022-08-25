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


    //Created a single for the connectivity class so its shared across the whole application and never recareated in a lifecycle.
    @Provides
    @Singleton
    fun provideConnectivityClient(): ConnectivityClient = ConnectivityClient.Factory.create()


    //Created a nav argument singleton b/c unfortunately there is no way to pass arguments when navigating between composables yet.
    @Provides
    @Singleton
    fun provideNavArguments(): NavArguments = NavArguments()
}
