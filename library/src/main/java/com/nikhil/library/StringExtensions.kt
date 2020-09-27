package com.nikhil.library

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.webkit.URLUtil
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.util.regex.Pattern

/**
 * MD5 Hash Calculator
 * Reasons to calculate MD5 of a [String]:
 * Save password in database, make some verification through insecure channel, check if file was calculated correctly.
 * Same with SHA hashes.
 */
val String.md5: String
    get() {
        //get MD5 hash as byte array
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        //convert byte array to hex string
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

/**
 * SHA1 Hash Calculator
 */
val String.sha1: String
    get() {
        val bytes = MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

/**
 * Checking If a String Is a Valid Email Address
 */
fun String.isEmailValid(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * Phone numbers for different countries have different formats. That’s why it can be done only with an external library.
 * This function uses (https://github.com/MichaelRocks/libphonenumber-android) library
 *
 * This function returns formatted phone number (starting with “+” and country code) if it’s valid, or null if not.
 * First argument is Android [Context], for example, [Activity].
 */
fun String.formatPhoneNumber(context: Context, region: String): String? {
    val phoneNumberKit = PhoneNumberUtil.createInstance(context)
    val number = phoneNumberKit.parse(this, region)
    if (!phoneNumberKit.isValidNumber(number))
        return null
    return phoneNumberKit.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
}

/**
 * What is inside the [String]?
 * It can be a number, it can be a word, or it can be a password with special characters.
 * Sometimes we need to find out what’s inside the string to know what to do next.
 */
val String.containsLatinLetter: Boolean
    get() = matches(Regex(".*[A-Za-z].*"))

val String.containsDigit: Boolean
    get() = matches(Regex(".*[0-9].*"))

val String.isAlphanumeric: Boolean
    get() = matches(Regex("[A-Za-z0-9]*"))

val String.hasLettersAndDigits: Boolean
    get() = containsLatinLetter && containsDigit

val String.isIntegerNumber: Boolean
    get() = toIntOrNull() != null

val String.toDecimalNumber: Boolean
    get() = toDoubleOrNull() != null

/**
 * Storing and Retrieving Local Settings
 * Android apps have several ways to store local settings, but the most simple and native way is [SharedPreferences].
 *
 * First argument is applicationContext: [Context]. If you run function from [Activity], it can pass itself (as Activity is subclass of Context) or better, applicationContext.
 *
 * Second argument is value: Map<String, Any>. [Map]s in Kotlin are easy to create and use.
 * In this extension Any can be any non-null value, but only five types will be saved: [Int], [Float], [Long], [Boolean] and [String].
 *
 * Third argument is clear
 * @param clear should be true if all fields in [SharedPreferences], which are not in
 * @param value, should be removed from SharedPreferences.
 *
 * Fourth argument is now
 * Another detail about [SharedPreferences] is background saving.
 * There are two ways to save SharedPreferences: [commit] and [apply].
 * The difference is that [commit] applies changes immediately. [apply] does it in the background, which can make your app faster, but doesn’t guarantee immediate saving.
 * In most cases [apply] is preferred, that’s why 'now' argument should be false almost always.
 *
 * Please note that here [SharedPreferences] are saved with [apply].
 * At the same time, it can be used immediately.
 * It happens because [SharedPreferences] are stored in phone memory.
 * If the app crashes right after, there’s a small chance that value won’t be saved, especially if it’s big.
 */
@SuppressLint("ApplySharedPref")
fun String.save(
    applicationContext: Context,
    value: Map<String, Any>,
    clear: Boolean = false,
    now: Boolean = false
) {
    val sp = applicationContext.getSharedPreferences(this, Context.MODE_PRIVATE).edit()
    if (clear)
        sp.clear()
    value.keys.forEach { key ->
        val v = value[key]
        if (v != null) {
            when (v) {
                is String -> sp.putString(key, v)
                is Float -> sp.putFloat(key, v)
                is Long -> sp.putLong(key, v)
                is Int -> sp.putInt(key, v)
                is Boolean -> sp.putBoolean(key, v)
            }
        }
    }
    if (now)
        sp.commit()
    else
        sp.apply()
}

fun String.load(applicationContext: Context): Map<String, Any> {
    val sp = applicationContext.getSharedPreferences(this, Context.MODE_PRIVATE)
    val keys = sp.all.keys
    val result = hashMapOf<String, Any>()
    keys.map { key ->
        val v = sp.all[key]
        if (v != null)
            result[key] = v
    }
    return result
}

/**
 * If [String] contains a valid JSON, function will return [JSONObject], otherwise null.
 */
val String.jsonObject: JSONObject?
    get() = try {
        JSONObject(this)
    } catch (e: JSONException) {
        null
    }

/**
 * If [String] contains a valid JSON, function will return [JSONArray], otherwise null.
 */
val String.jsonArray: JSONArray?
    get() = try {
        JSONArray(this)
    } catch (e: JSONException) {
        null
    }

/**
 * If [String] contains a path or URL, it returns last component (after last ‘/’).
 *
 * Two important points:
 * 1. Path can end with trailing slash. The extension function should return path component before it; otherwise, it won’t process URLs.
 * 2. Some systems (Microsoft Windows, for example) use backslash instead of slash. We should handle it too.
 */
val String.lastPathComponent: String
    get() {
        var path = this
        if (path.endsWith("/"))
            path = path.substring(0, path.length - 1)
        var index = path.lastIndexOf('/')
        if (index < 0) {
            if (path.endsWith("\\"))
                path = path.substring(0, path.length - 1)
            index = path.lastIndexOf('\\')
            if (index < 0)
                return path
        }
        return path.substring(index + 1)
    }

/**
 * Color From a String
 *
 * Color is represented differently in different environments.
 * Android uses simple integers. It has [Color] class, but it just contains helper functions.
 * Another representation is AWT Color class, which incapsulates four integer values for red, green, blue and alpha components.
 */

/**
 * Parsing hex String to AWT Color
 */
//val String.awtColor: Color?
//    get() {
//        val r = substring(1, 3).toIntOrNull(16) ?: return null
//        val g = substring(3, 5).toIntOrNull(16) ?: return null
//        val b = substring(5, 7).toIntOrNull(16) ?: return null
//        return Color(r, g, b)
//    }

/**
 * Parsing hex String to Android Color
 *
 * This class returns [Int] because Android uses [Int] in its views.
 *
 * You may ask: Why use extension if it replaces one line of code with another line of code?
 * There are two differences:
 * 1. 'asColor' extension can be chained. For example: "hexString.asColor?.toString()"
 * 2. 'null' instead of exception. You don’t need to wrap it in try-catch block. You’ll just get 'null' if it’s not a valid color.
 */
val String.asColor: Int?
    get() = try {
        Color.parseColor(this)
    } catch (e: java.lang.IllegalArgumentException) {
        null
    }

/**
 * Format String as Credit Card Number
 *
 * This simple extension adds space after each four characters of a string
 */
val String.creditCardFormatted: String
    get() {
        val preparedString = replace(" ", "").trim()
        val result = StringBuilder()
        for (i in preparedString.indices) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ")
            }
            result.append(preparedString[i])
        }
        return result.toString()
    }

/**
 * When you work with an API and get the coordinates of an object, you may get them as two different fields.
 * But sometimes it’s one field with a comma-separated latitude and longitude.
 *
 * Similarly, you can create an extension converting String to [LatLng] from the Google Maps package.
 * In this case, you won’t even need to specify the location provider.
 */
fun String.toLocation(provider: String): Location? {
    val components = this.split(",")
    if (components.size != 2)
        return null

    val lat = components[0].toDoubleOrNull() ?: return null
    val lng = components[1].toDoubleOrNull() ?: return null
    val location = Location(provider);
    location.latitude = lat
    location.longitude = lng
    return location
}

/**
 * We usually think about an internet address as a string.
 * We can type it and put it into quotes.
 * For example, “https://medium.com.”
 *
 * But for internal use, Android has a special type: [Uri].
 * It’s easy to convert one into the other.
 * The extension below allows us to convert a String into an [Uri] with verification.
 * If it’s not a valid [Uri], it returns null
 */
val String.asUri: Uri?
    get() = try {
        if (URLUtil.isValidUrl(this))
            Uri.parse(this)
        else
            null
    } catch (e: Exception) {
        null
    }

