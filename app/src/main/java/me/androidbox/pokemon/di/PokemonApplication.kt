package me.androidbox.pokemon.di

import android.app.Application
import me.androidbox.pokemon.di.components.ApplicationComponent
import me.androidbox.pokemon.di.components.DaggerApplicationComponent
import me.androidbox.pokemon.di.modules.ApplicationModule
import me.androidbox.pokemon.di.modules.NetworkModule
import timber.log.Timber

class PokemonApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this@PokemonApplication))
            .networkModule(NetworkModule())
            .build()
    }
}