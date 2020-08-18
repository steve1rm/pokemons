package me.androidbox.pokemon.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class EpoxyBaseModel : EpoxyModelWithHolder<EpoxyBaseViewHolder>(), PreloaderImage {
    protected val subscriptions: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onViewDetachedFromWindow(holder: EpoxyBaseViewHolder) {
        subscriptions.clear()
        super.onViewDetachedFromWindow(holder)
    }

    override fun preloaderImages(): List<String> {
        return emptyList()
    }

    fun hideKeyboard(view: View) {
        val imm =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}