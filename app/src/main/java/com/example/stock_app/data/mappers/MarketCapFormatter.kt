package com.example.stock_app.data.mappers



fun String.toMarketCap(): String{
    return try {
        val value = this.toLongOrNull() ?: return this
        when {
            value >= 1_000_000_000_000 -> "$${(value / 1_000_000_000_000.0).format(2)} Trillion"
            value >= 1_000_000_000 -> "$${(value / 1_000_000_000.0).format(2)} Billion"
            value >= 1_000_000 -> "$${(value / 1_000_000.0).format(2)} Million"
            value >= 1_000 -> "$${(value / 1_000.0).format(2)} Thousand"
            else -> value.toString()
        }
    } catch (e: Exception) {
        this
    }
}

fun Double.format(digits: Int) = "%.${digits}f".format(this).trimEnd('0').trimEnd('.')