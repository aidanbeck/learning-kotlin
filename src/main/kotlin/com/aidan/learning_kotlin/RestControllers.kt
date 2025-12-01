package com.aidan.learning_kotlin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/articles") // why is this named api/v1/articles? is this a standard?
class ArticleController {

    // articles dummy data, will replace with db access
    val articles = mutableListOf( // modifiable list.
        Article( // "New" keyword is no longer necessary. Just call ClassName()
            "My Title",      // implicit parameter example
            content = "My Content" // explicit parameter example
            // values like views & createdAt do not *have* to be passed, because they have default values.
        )
    )

    // long way of returning articles
    @GetMapping
    fun articles() : MutableList<Article> { // return type declared
        return articles; // return value declared
    }

    // short way of returning articles
    fun articles2() = articles
    // articles return type & value is inferred
    // TODO read up on exactly how this works

}