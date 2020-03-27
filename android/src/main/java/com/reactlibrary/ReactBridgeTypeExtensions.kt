package com.reactlibrary

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap


fun ReadableMap.getStringList(key: String): List<String> {
    return if (this.hasKey(key)) {
        this.getArray(key)?.toIterable()?.toList().orEmpty()
    } else
        emptyList();
}

fun ReadableArray.toIterable(): Iterable<String> {
    val size = this.size()
    val accumulator = mutableListOf<String>()
    for (index in size - 1 downTo 0) {
        val value = this.getString(index)
        value?.let { accumulator.add(it) }
    }
    return accumulator
}

/**
 * This get method does not throw a runtime exception if key does not exist,
 * but rather returns a nullable @param String?
 */
fun ReadableMap.getStringSilent(key: String): String? {
    return when (this.hasKey(key)) {
        true -> this.getString(key)
        else -> null
    }
}

/**
 * This get method does not throw a runtime exception if key does not exist,
 * but rather returns a nullable @param Int?
 */
fun ReadableMap.getIntSilent(key: String): Int? {
    return when (this.hasKey(key)) {
        true -> this.getInt(key)
        else -> null
    }
}

/**
 * This get method does not throw a runtime exception if key does not exist,
 * but rather returns a nullable @param Boolean?
 */
fun ReadableMap.getBooleanSilent(key: String): Boolean? {
    return when (this.hasKey(key)) {
        true -> this.getBoolean(key)
        else -> null
    }
}