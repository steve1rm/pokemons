package me.androidbox.pokemon.screens

import android.view.View
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView
import me.androidbox.pokemon.R
import org.hamcrest.Matcher

class PokemonListScreen : Screen<PokemonListScreen>() {
    val container: KView = KView { withId(R.id.fragment_pokemon_list) }

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val name: KTextView = KTextView(parent) { withId(R.id.tvName) }
    }

    val pokomons: KRecyclerView = KRecyclerView(
        { withId(R.id.rvPokemons) },
        { itemType(::Item) }
    )
}