package me.androidbox.pokemon.screens

import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView
import me.androidbox.pokemon.R

class PokemonDetailsScreen : Screen<PokemonDetailsScreen>() {
    val container: KView = KView { withId(R.id.bottom_sheet_layout) }
    val name: KTextView = KTextView { withId(R.id.tvName) }
    val height: KTextView = KTextView { withId(R.id.tvHeight) }
    val weight: KTextView = KTextView { withId(R.id.tvWeight) }
    val baseExperience: KTextView = KTextView { withId(R.id.tvBaseExperience) }
}