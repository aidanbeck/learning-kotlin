package com.aidan.learning_kotlin

import java.time.LocalDateTime

data class Article(
    var title: String,
    var content: String,
    var createdAt: LocalDateTime? = LocalDateTime.now(), // default value

    // a "slug" is the directory of the content under the website domain
    // https://blog/my-first-title  My First Title
    var slug: String? = title.toSlug() // calls extension function
)
