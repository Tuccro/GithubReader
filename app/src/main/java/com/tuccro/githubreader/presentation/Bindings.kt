package com.tuccro.githubreader.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView
import timber.log.Timber

class Bindings {
    companion object {
        @JvmStatic
        @BindingAdapter("bind:url")
        fun setUrl(draweeView: SimpleDraweeView, url: String?) {
            Timber.d("url = $url")
            draweeView.setImageURI(url)
        }

        @JvmStatic
        @BindingAdapter("bind:textResId", "bind:textArg", requireAll = true)
        fun setText(view: TextView, textResId: Int, textArg: Int) {
            view.text = view.resources.getString(textResId, textArg)
        }
    }
}