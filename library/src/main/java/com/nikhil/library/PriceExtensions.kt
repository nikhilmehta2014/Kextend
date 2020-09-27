package com.nikhil.library

import java.text.DecimalFormat
import java.util.*

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

/**
 * Convert 'cents' in [Int] format to 'dollars' in [Double] format
 */
fun Int.centsToDollars(): Double = this.toDouble() / 100.0

/**
 * Convert 'cents' in [Int] format to 'dollars' in [String] format
 */
fun Int.centsToDollarsFormat(currency: String): String {
    val dollars = this / 100
    val cents = this % 100
    return String.format(Locale.US, "%s%d.%02d", currency, dollars, cents)
}

/**
 * Another useful extension when working with prices is formatting with group separation.
 * In most cases, inside one app, we have only one set of rules for price formatting. One extension can be used in the whole app to show prices.
 *
 * In this extension, we use two fractional (monetary) digits, show a comma between, and split each of our three digits with a dot to make the price easier to read.
 * This time we’ll use Double.
 * Converting Int to Double can be done with the [centsToDollars] extension.
 */
fun Double.toPrice(): String {
    val pattern = "#,###.00"
    val decimalFormat = DecimalFormat(pattern)
    decimalFormat.groupingSize = 3

    return "€" + decimalFormat.format(this)
}

