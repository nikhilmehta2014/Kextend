package com.nikhil.library

import android.os.Build
import java.util.*

/**
 * It’s questionable if you should extend Any. If you do, the function appears globally, so basically, it’s the same as declaring a global function.
 *
 *  As it doesn’t require any context, we’ll make it as an Any extension
 */
@ExperimentalStdlibApi
val Any.deviceName: String
    get() {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer))
            model.capitalize(Locale.getDefault())
        else
            manufacturer.capitalize(Locale.getDefault()) + " " + model
    }