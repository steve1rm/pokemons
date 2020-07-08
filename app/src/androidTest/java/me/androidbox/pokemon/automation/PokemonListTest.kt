package me.androidbox.pokemon.automation

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.platform.app.InstrumentationRegistry
import me.androidbox.pokemon.pages.PokemonListPage
import me.androidbox.pokemon.presentation.screens.HomeActivity
import me.androidbox.pokemon.rules.TestComponentRule
import me.androidbox.pokemon.utils.Utilities
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

class PokemonListTest {
    private val testComponentRule =
        TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    private val main = IntentsTestRule(HomeActivity::class.java, false, false)

    private val mockWebServer: MockWebServer by lazy {
        MockWebServer()
    }

    @get:Rule
    val chain = RuleChain.outerRule(testComponentRule).around(main)

    @Before
    fun setup() {
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldDisplayPokemons() {
        mockWebServer.enqueue(MockResponse().setBody(Utilities.loadFromResources("json/pokemonlist.json")))
        ActivityScenario.launch(HomeActivity::class.java)

        PokemonListPage
            .shouldBeVisible()
    }
}
