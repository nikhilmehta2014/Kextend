package com.nikhil.library

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import com.google.android.material.snackbar.Snackbar

fun View.onThrottledClick(
    throttleDelay: Long = 500L,
    onClick: (View) -> Unit
) {
    setOnClickListener {
        onClick(this)
        isClickable = false
        postDelayed({ isClickable = true }, throttleDelay)
    }
}

/**
 * View's visibility extensions.
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.toggleVisibility(): View {
    visibility = if (visibility == View.VISIBLE) {
        View.GONE
    } else {
        View.VISIBLE
    }
    return this
}


/**
 * Snackbar's extensions.
 */


fun View.showSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

fun View.showSnackbar(text: String, duration: Int) {
    Snackbar.make(this, text, duration).show()
}

fun View.showSmallSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

fun View.showLongSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).show()
}

fun View.showIndefiniteSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE).show()
}


fun Activity.findAndSetTextInTextView(@IdRes id: Int, text: String) {
    findViewById<TextView>(id).text = text
}

fun Activity.findAndSetTextInEditText(@IdRes id: Int, text: String) {
    findViewById<EditText>(id).setText(text)
}


/**
 * Toast extensions.
 */

fun Context.showSmallLengthToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showLongLengthToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}