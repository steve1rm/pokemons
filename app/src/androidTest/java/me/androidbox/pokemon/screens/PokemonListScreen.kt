package me.androidbox.pokemon.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import me.androidbox.pokemon.R
import org.hamcrest.Matcher

class PokemonListScreen : Screen<PokemonListScreen>() {
    val container: KView = KView { R.id.PokemonListFragment }

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val name: KTextView = KTextView(parent) { withId(R.id.tvName) }
    }

    val pokomons: KRecyclerView = KRecyclerView(
        { withId(R.id.rvPokemons) },
        { itemType(::Item)} )
}
