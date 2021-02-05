package com.nikhil.library

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

/**
 * When we create an extension that is taking a generic type we can apply it for every class extends or implements that generic.
 *
 * This one is very useful when we want to edit to a third-party library or an API so instead of inheriting that class,
 * we can make use of extensions which will help us to reduce that boilerplate code.
 */

/**
 * Use case:
 * Suppose we have an app and we need to search for a name using this extension to set list of names to AutoCompleteTextView Adapter, or
 * even if we need to copy the name list after choosing a list of RandomNames so this will help us to get the Name list and store the in a clipboard .
 */

abstract class User(open var id: Int, open var name: String)

data class Student(override var id: Int, override var name: String) : User(id, name)

fun <T : User> List<T>.getUserNameList(): List<String> {
    return this.map {
        it.name
    }
}

fun <T> T?.orDefault(default: T): T {
    return this ?: default
}


/**
 * We often require to refresh a list by removing the existing items and updating them with the new ones or
 * sometimes we need to check the permission status in our app to access some features.
 *
 * Note: The Generic type is specified in the below List extensions to avoid making different extensions for different Object types.
 */
fun <T : Any> ArrayList<T>.refreshList(items: List<T>): ArrayList<T> {
    this.clear()
    this.addAll(items)
    return this
}

fun <T : Any> ArrayList<T>.addOnlyNewItems(items: List<T>): ArrayList<T> {
    items.forEach {
        if (!this.contains(it))
            this.add(it)
    }
    return this
}

fun Activity.hasPermission(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}