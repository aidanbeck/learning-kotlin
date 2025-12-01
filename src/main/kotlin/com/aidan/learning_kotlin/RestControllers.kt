package com.aidan.learning_kotlin

import org.springframework.web.bind.annotation.*

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

    @GetMapping("/{title}")
    fun article(@PathVariable title: String): Article {
        for (article in articles) {
            if (article.title == title) {
                return article
            }
        }
        throw IllegalArgumentException("Cannot read. Article '$title' not found.")
        // is this exception best practice!
    }

    @PostMapping
    fun addArticle (@RequestBody article: Article) {
        articles.add(article);
    }

    @PutMapping
    fun updateArticle (@RequestBody article: Article): Article {

        val title = article.title

        for (i in 0..articles.size) {
            if (articles[i].title == title) {
                articles[i] = article
                return articles[i]
            }
        }

        throw IllegalArgumentException("Cannot update. Article '$title' not found.")
    }

    @DeleteMapping("/{title}")
    fun deleteArticle(@PathVariable title: String) {
        for (article in articles) {
            if (article.title == title) {
                articles.remove(article)
            }
        }
        throw IllegalArgumentException("Cannot delete. Article '$title' not found.")
    }


}