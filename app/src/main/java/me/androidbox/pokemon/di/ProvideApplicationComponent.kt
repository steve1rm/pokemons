package me.androidbox.pokemon.di

import android.app.Activity
import me.androidbox.pokemon.di.components.ApplicationComponent

object ProvideApplicationComponent {

    @JvmStatic
    fun getApplicationComponent(activity: Activity): ApplicationComponent {
        return (activity.application as PokemonApplication).applicationComponent
    }
}