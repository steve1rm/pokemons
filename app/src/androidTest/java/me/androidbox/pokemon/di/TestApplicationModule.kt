package me.androidbox.pokemon.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import javax.inject.Singleton

@Module
class TestApplicationModule(private val application: PokemonApplication) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Application = application

    @Singleton
    @Provides
    fun provideContext(): Context = application

    @Singleton
    @Provides
    fun provideSchedulers(): PokemonSchedulers {
        return object : PokemonSchedulers {
            override fun ui(): Scheduler {
                return Schedulers.trampoline()
            }

            override fun background(): Scheduler {
                return Schedulers.trampoline()
            }
        }
    }
}
