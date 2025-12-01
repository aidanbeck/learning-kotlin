package com.aidan.learning_kotlin

import java.util.*

fun String.toSlug() = lowercase(Locale.getDefault()) // what is Locale.getDefault()? TODO read up on this.
    .replace("\n", " ")
    .replace("[^a-z\\d\\s]s]".toRegex(), " ")
    .split(" ")
    .joinToString("-")
    .replace("-+".toRegex(), "-")
