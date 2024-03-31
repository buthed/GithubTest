package com.tematihonov.githubtest.presentation.ui.utils

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.tematihonov.githubtest.R

fun ImageView.loadImageWithCoil(url: String) {
    this.load(url) {
        crossfade(true)
        placeholder(R.drawable.avatar_stub)
        transformations(CircleCropTransformation())
    }
}