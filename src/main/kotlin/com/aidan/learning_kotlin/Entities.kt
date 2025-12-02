package com.aidan.learning_kotlin
// TODO make sure to remember that the package name must be at the top! I forgot this and it took some time to realize

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Article (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,
    var content: String,
    var createdAt: LocalDateTime? = LocalDateTime.now(), // default value
    var slug: String? = title.toSlug() // calls extension function
)
/*
    Notes:

    a "slug" is the directory of the content under the website domain. My Title -> my-title

    Article should not be a data class because it "overrides methods like equals and hashcode". STUDY what are all the differences between a class and a data class?

    In hibernate, you always need a 0 argument constructor. Our jpa plugin creates this for us under the hood!

    creating "var"s in the fields creates getters and setters STUDY how exactly does this work? How are they accessed?

 */
