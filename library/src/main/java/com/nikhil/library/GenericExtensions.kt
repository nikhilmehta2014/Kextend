package com.nikhil.library

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