package com.nikhil.library

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import kotlin.experimental.and

/**
 * This fun can be applied to any class that implements [Serializable]
 */
@Throws(IOException::class)
fun Serializable.serialize(): String {
    val serialObj = ByteArrayOutputStream()
    val objStream = ObjectOutputStream(serialObj)
    objStream.writeObject(this)
    objStream.close()
    return encodeBytes(serialObj.toByteArray())
}

/**
 * This fun can be applied to any class that implements [Serializable]
 */
@Throws(IOException::class, ClassNotFoundException::class)
fun String?.deserialize(): Any? {
    if (this.isNullOrEmpty()) return null
    val serialObj = ByteArrayInputStream(decodeBytes(this))
    val objStream = ObjectInputStream(serialObj)
    return objStream.readObject()
}

/**
 * Encode [ByteArray] and output encoded [String]
 */
private fun encodeBytes(bytes: ByteArray): String {
    val strBuf = StringBuffer()
    for (i in bytes.indices) {
        strBuf.append(((bytes[i].toInt() shr 4 and 0xF) + 'a'.toInt()).toChar())
        strBuf.append(((bytes[i] and 0xF) + 'a'.toInt()).toChar())
    }
    return strBuf.toString()
}

/**
 * Decode [String] and out the [ByteArray]
 */
private fun decodeBytes(str: String): ByteArray {
    val bytes = ByteArray(str.length / 2)
    var i = 0
    while (i < str.length) {
        var c = str[i]
        bytes[i / 2] = (c - 'a' shl 4).toByte()
        c = str[i + 1]
        bytes[i / 2] = bytes[i / 2].plus(((c - 'a'))).toByte()
        i += 2
    }
    return bytes
}

/**
 * JSON and XML are two popular formats for data load, save, exchange, etc. It’s hard to imagine API call without one of these formats.
 * Java offers rather comfortable classes: [JSONObject] and [JSONArray].
 * Kotlin can use any Java class, so it’s a Kotlin way to parse and write JSON too. [JSONObject] and [JSONArray] are available in Android.
 *
 * Why extend these classes?
 * The problem is that they throw exception if something goes wrong.
 * If anything goes wrong.
 * For example, if you try to get the wrong data type, or if you refer to a field that doesn’t exist.
 * <code>
 *     try {
val obj = JSONObject()
obj.put("str", "abc")
val strAsInt = obj.getInt("str") // org.json.JSONException: Value abc at str of type java.lang.String cannot be converted to int
val noKey = obj.getString("iamnothere") // org.json.JSONException: No value for iamnothere
}
catch (e: JSONException) {
e.printStackTrace()
}
<code>
 *
 * There’s nothing wrong with exceptions; it’s a good way to handle such situations.
 * But sometimes we need to pull only existing fields and ignore (keep as null) fields, which are not in API response.
 * Without extensions it can be done this way:
 * <code>
 *     val firstName: String?
 *     try {
 *          firstName = json.getString("first_name")
 *      } catch (e: JSONException) {
 *          firstName = null
 *      }
 *
 *      val lastName: String?
 *      try {
 *          lastName = json.getString("last_name")
 *      } catch (e: JSONException) {
 *          lastName = null
 *      }
 *
 * Looks very long, right?
 * Now imagine that server returns "null" instead of null. Code will grow for each field.
 * This can be solved with extension:
 *
 * </code>
 */
fun JSONObject.getIntOrNull(name: String): Int? =
    try {
        getInt(name)
    } catch (e: JSONException) {
        val strValue = getStringOrNull(name)
        strValue?.toIntOrNull()
    }

fun JSONObject.getDoubleOrNull(name: String): Double? =
    try {
        getDouble(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getLongOrNull(name: String): Long? =
    try {
        getLong(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getStringOrNull(name: String): String? =
    try {
        getString(name).trim()
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getBooleanOrNull(name: String): Boolean? =
    try {
        getBoolean(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getObjectOrNull(name: String): JSONObject? =
    try {
        getJSONObject(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getArrayOrNull(name: String): JSONArray? =
    try {
        getJSONArray(name)
    } catch (e: JSONException) {
        null
    }

fun JSONObject.getArrayOrEmpty(name: String): JSONArray =
    try {
        getJSONArray(name)
    } catch (e: JSONException) {
        JSONArray()
    }

/**
 * This extension has some additional features like String trimming.
 * Features like replacing "null" to null can be added this way:
 */
fun JSONObject.getStringOrNull2(name: String): String? {
    try {
        val str = getString(name).trim()
        if (str == "null")
            return null
        return str
    } catch (e: JSONException) {
        null
    }
    return null
}