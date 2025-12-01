package com.aidan.learning_kotlin

import java.time.LocalDateTime

data class Article(
    val title: String, // constant
    val content: String?, // nullable constant
    val createdAt: LocalDateTime = LocalDateTime.now(), // default value
    var views: Integer, // variable

    // a "slug" is the directory of the content under the website domain
    // https://blog/my-first-title  My First Title
    val slug: String = title.toSlug() // calls extension function
)
