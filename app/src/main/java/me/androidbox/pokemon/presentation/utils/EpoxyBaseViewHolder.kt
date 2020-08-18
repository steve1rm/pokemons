package me.androidbox.pokemon.presentation.utils

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.preload.Preloadable

class EpoxyBaseViewHolder(
    private val preLoader: (View) -> List<View> = { emptyList() }
) : EpoxyHolder(), Preloadable {
    companion object {
        @JvmStatic
        private val TAG = EpoxyBaseViewHolder::class.java.simpleName
    }

    lateinit var itemView: View

    override fun bindView(itemView: View) {
        this.itemView = itemView
    }

    override val viewsToPreload: List<View>
        get() {
            return preLoader.invoke(itemView)
        }
}