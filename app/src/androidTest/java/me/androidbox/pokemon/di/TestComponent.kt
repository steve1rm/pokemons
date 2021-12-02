package me.androidbox.pokemon.di

import dagger.Component
import me.androidbox.pokemon.automation.PokemonListTest
import me.androidbox.pokemon.di.components.ApplicationComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestApplicationModule::class,
        TestNetworkModule::class
    ]
)
interface TestComponent : ApplicationComponent {
    fun inject(activity: PokemonListTest)
}