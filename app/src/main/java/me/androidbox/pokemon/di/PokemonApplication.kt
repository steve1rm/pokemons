package me.androidbox.pokemon.di

import android.app.Application
import me.androidbox.pokemon.di.components.ApplicationComponent
import timber.log.Timber

class PokemonApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        applicationComponent = DaggerApplicationComponent.create()
    }
}
