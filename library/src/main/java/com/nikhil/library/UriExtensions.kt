package com.nikhil.library

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.thefinestartist.finestwebview.FinestWebView

/**
 * Now when we have an [Uri], we may want to open it in a browser. There are two ways to do this:
 * 1. Open it inside the
 * 2. Open it in an external browser
 *
 * We usually want to keep users inside our app, but some schemas can’t be opened inside.
 * For example, we don’t want to open `instagram://` in the in-app browser.
 * Even more, we want to open only `http://` and `https://`.
 */

/**
 * Open [Uri] dynamically, based on the schema
 */
fun Uri.open(context: Context): Boolean =
    if (this.scheme == "http" || this.scheme == "https") {
        openInside(context)
        true
    } else
        openOutside(context)

/**
 * open [Uri] inside the app
 *
 * To open a web page inside an app, we need to either create a separate activity, or use a library that’ll do it for us.
 * For simplicity, I chose the second way and included the [FinestWebView] library.
 */
fun Uri.openInside(context: Context) =
    FinestWebView.Builder(context).show(this.toString())

/**
 * open [Uri] inside an external browser
 */
fun Uri.openOutside(context: Context): Boolean =
    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, this)
        context.startActivity(browserIntent)
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }