package com.nikhil.library

import java.text.DecimalFormat

/**
 * Use case:
 * E-commerce app, where every product has its own price so theses extensions will help us to format the price to a pattern that we want.
 */

/**
 * Formats a [String] to a decimal format.
 */
fun String.toPriceAmount(): String {
    val dec = DecimalFormat("###,###,###.00")
    return dec.format(this.toDouble())
}

/**
 * Formats a [Double] to a decimal format.
 */
fun Double.toPriceAmount(): String {
    val dec = DecimalFormat("###,###,###.00")
    return dec.format(this)
}