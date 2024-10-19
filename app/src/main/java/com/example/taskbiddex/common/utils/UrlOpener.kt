package com.example.taskbiddex.common.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.taskbiddex.R

class UrlOpener(url: String, activity: Activity) {
    init {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                activity,
                activity.resources.getString(R.string.err_happened_in_link),
                Toast.LENGTH_SHORT
            ).show()

        }

    }
}