package com.tematihonov.githubtest.presentation.ui.utils


import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.tematihonov.githubtest.R

fun usersAccountDateCreater(context: Context, createdAt: String): String {
    return "${getString(context, R.string.on_github_from)} ${createdAt.take(10)}"
}