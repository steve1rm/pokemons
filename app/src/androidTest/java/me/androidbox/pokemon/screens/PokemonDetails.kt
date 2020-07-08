package me.androidbox.pokemon.screens

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import me.androidbox.pokemon.R

class PokemonDetails : Screen<PokemonDetails>() {
    val container: KView = KView { withId(R.id.bottom_sheet_layout) }
    val name: KTextView = KTextView { withId(R.id.tvName) }
    val height: KTextView = KTextView { withId(R.id.tvHeight) }
    val weight: KTextView = KTextView { withId(R.id.tvWeight) }
    val baseExperience: KTextView = KTextView { withId(R.id.tvBaseExperience) }
}
