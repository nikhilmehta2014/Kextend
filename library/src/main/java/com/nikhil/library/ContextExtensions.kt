package com.nikhil.library

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*

/**
 * [hideKeyboard] when inside [Fragment]
 */
fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}

/**
 * [hideKeyboard] when inside [Activity]
 */
fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

/**
 * Actual code for hiding the keyboard
 */
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * In Android apps, it’s a good practice to show the version number on the about or support screen.
 * It’ll help users see if they need an update and gives valuable information when reporting a bug.
 * Standard Android functions require several lines of code and exception handling. This extension will allow you to get the version number or code in one line
 */
val Context.versionName: String?
    get() = try {
        val pInfo = packageManager.getPackageInfo(packageName, 0);
        pInfo?.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }

val Context.versionCode: Long?
    get() = try {
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            pInfo?.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            pInfo?.versionCode?.toLong()
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }

/**
 * This extension returns the screen size in pixels
 */
@Suppress("DEPRECATION")
val Context.screenSize: Point
    get() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

/**
 * The Google Maps app is pre-installed on most Android phones and tablets.
 * The easiest solution is to open navigation in the Android Maps app.
 * And if it’s not installed, just open it in a web browser.
 */
fun Context.directionsTo(location: Location) {
    val lat = location.latitude
    val lng = location.longitude
    val uri = String.format(Locale.US, "http://maps.google.com/maps?daddr=%f,%f", lat, lng)
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }
}

fun Context.vibrate(duration: Long) {
    val vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vib.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vib.vibrate(duration)
    }
}

/**
 * Show [Toast] with default duration of [Toast.LENGTH_SHORT]
 */
fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}