package com.reactlibrary

import com.facebook.react.bridge.*

fun ReadableMap.getStringList(key: String): List<String> {
    return if (this.hasKey(key)) {
        this.getArray(key)?.toStringIterable()?.toList().orEmpty()
    } else
        emptyList();
}

fun ReadableArray.toStringIterable(): Iterable<String> {
    val size = this.size()
    val accumulator = mutableListOf<String>()
    for (index in size - 1 downTo 0) {
        val value = this.getString(index)
        value?.let { accumulator.add(it) }
    }
    return accumulator
}

fun ReadableMap.getMapList(key: String): List<ReadableMap> {
    return if (this.hasKey(key)) {
        this.getArray(key)?.toMapIterable()?.toList().orEmpty()
    } else
        emptyList();
}

fun ReadableArray.toMapIterable(): Iterable<ReadableMap> {
    val size = this.size()
    val accumulator = mutableListOf<ReadableMap>()
    for (index in size - 1 downTo 0) {
        val value = this.getMap(index)
        value?.let { accumulator.add(it) }
    }
    return accumulator
}

/**
 * This get method does not throw a runtime exception if key does not exist,
 * but rather returns a nullable @param String?
 */
fun ReadableMap.getNullableString(key: String): String? {
    return when (this.hasKey(key)) {
        true -> this.getString(key)
        else -> null
    }
}

/**
 * This get method does not throw a runtime exception if key does not exist,
 * but rather returns a nullable @param Int?
 */
fun ReadableMap.getNullableInt(key: String): Int? {
    return when (this.hasKey(key)) {
        true -> this.getInt(key)
        else -> null
    }
}

/**
 * This get method does not throw a runtime exception if key does not exist,
 * but rather returns a nullable @param Boolean?
 */
fun ReadableMap.getNullableBoolean(key: String): Boolean? {
    return when (this.hasKey(key)) {
        true -> this.getBoolean(key)
        else -> null
    }
}