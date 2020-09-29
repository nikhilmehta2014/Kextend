package com.nikhil.library

/**
 * [T] is a generic type
 */
fun <T> List<T>.midElement(): T {
    if (isEmpty()) {
        throw NoSuchElementException("List is empty")
    }
    return this[size / 2]
}

/**
 * Since extensions do not actually insert members into classes, there’s no efficient way for an extension property to have a backing field.
 * This is why initializers are not allowed for extension properties.
 * Their behavior can only be defined by explicitly providing getters/setters.
 *
 * For ex-
 * var BaseClass.index : Int = 10       //compile error
 */
val <T> List<T>.midIndex: Int
    get() = if (size == 0) 0 else size / 2