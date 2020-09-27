package com.nikhil.library

/**
 * We can use the below extension functions instead of
 * <code>
 *     if(someObject == null){}
 *     or
 *     if(someObject != null){}
 */

fun Any?.isNull() = this == null

fun Any?.isNotNull() = !isNull()