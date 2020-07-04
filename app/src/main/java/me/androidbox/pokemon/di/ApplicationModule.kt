package me.androidbox.pokemon.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: PokemonApplication) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application
}
