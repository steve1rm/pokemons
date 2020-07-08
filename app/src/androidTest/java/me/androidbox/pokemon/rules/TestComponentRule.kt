package me.androidbox.pokemon.rules

import android.content.Context
import me.androidbox.pokemon.di.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestComponentRule(private val context: Context): TestRule {

    private var testComponent: TestComponent? = null

    private fun setupTestDependencies() {
        val application = context.applicationContext as PokemonApplication

        val testComponent = DaggerTestComponent
            .builder()
            .testApplicationModule(TestApplicationModule(application))
            .testNetworkModule(TestNetworkModule())
            .build()

        application.applicationComponent = testComponent
    }

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                setupTestDependencies()
                base?.evaluate()
            }
        }
    }
}
