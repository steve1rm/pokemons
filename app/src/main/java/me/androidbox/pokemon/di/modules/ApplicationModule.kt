package me.androidbox.pokemon.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import me.androidbox.pokemon.di.PokemonApplication
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: PokemonApplication) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application
}
