package me.androidbox.pokemon.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import me.androidbox.pokemon.di.PokemonApplication
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: PokemonApplication) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application

    @Singleton
    @Provides
    fun provideSchedulers(): PokemonSchedulers {
        return object : PokemonSchedulers {
            override fun ui(): Scheduler {
                return Schedulers.io()
            }

            override fun background(): Scheduler {
                return AndroidSchedulers.mainThread()
            }

            override fun test(): Scheduler {
                return TestScheduler()
            }
        }
    }

    interface PokemonSchedulers {
        fun ui(): Scheduler

        fun background(): Scheduler

        fun test(): Scheduler
    }
}
