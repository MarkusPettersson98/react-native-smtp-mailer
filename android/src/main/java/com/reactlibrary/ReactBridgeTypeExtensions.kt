package com.reactlibrary

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap


fun ReadableMap.getStringList(key: String): List<String> {
    return if (this.hasKey(key)) {
        this.getArray(key)?.toIterable()?.toList().orEmpty()
    }
    else
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