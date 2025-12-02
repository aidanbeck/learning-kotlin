package com.aidan.learning_kotlin

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

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

    @GetMapping
    fun articles() = articles
    /*
        Notes:

        "articles" value is inferred as the return. STUDY how does this this work? Does it just return the value it equals?

        The function return type MutableList<Article> is also inferred.

        They can also be specified like so:

        fun articles() : MutableList<Article> {
            return articles
        }
    */

    @GetMapping("/{title}")
    fun articles(@PathVariable title: String) =
        articles.find { article -> article.title == title } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    /*
        Notes:

        "articles" method can be overloaded, since it takes a PathVariable String parameter.

        This makes "articles" mean three things within the class. The data itself, the getter for all, and the getter for a single article.
        STUDY is this really best practice?

        .find is a higher order function. It accepts a function as a parameter.
        higher order functions can be passed with just  {} brackets, instead of ({})
        for example, find( { a -> a++ } ) can be shortened to find { a -> a++ }

        ?: is a ternary operator. If null, throw exception.

        .find is a MutableList method that takes a function as an input.
        It inputs each element of the list into the function.
        Then, for each, it runs our provided condition. If the condition is true, it returns not the condition,
        but the element that led to the true condition.
        STUDY how does this work? Can you use .find to modify each element as well? Why use .find specifically?

        ResponseStatusException I recognize from Java. It is the best practice for REST exceptions using an HTTP status.

        You can use == instead of .equals() to compare strings, unlike Java
     */

    @PostMapping
    fun addArticle (@RequestBody article: Article): Article {
        articles.add(article)
        return article
    }
    /*
        Notes:

        The return type must be defined in a more complicated function like this. STUDY When must it be defined, and when can it be inferred?

        Remember to return the article in a post request
     */

    @PutMapping("/{title}")
    fun updateArticle (@RequestBody article: Article, @PathVariable title: String): Article {
        val existingArticle = articles.find { it.title == title } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        existingArticle.content = article.content
        return article
    }
    /*
        Notes:

        Put Requests should have a /{title} identifier instead of just an object, so that the object can be sent to the right place.

        uses "it" instead of "article" to avoid confusion with the input article. STUDY is using "it" standard practice?
        ANSWER "it" is a keyword for the single parameter in a lambda function. By using "it", we are targeting each element in the list.

        This function only updates the content. Other methods will need to be used
        It also only returns the original article, which can be different than the updated article STUDY is this truly best practice?
     */

    @DeleteMapping("/{title}")
    fun deleteArticle(@PathVariable title: String) {
        articles.removeIf { article -> article.title == title }
    }
    /*
        Notes:

        .removeIf() compares titles for each element, and removes the element with a true result

        STUDY What does this return? Why doesn't this need an exception for if a title is not found?
        HALF-ANSWER: It sends back a 200 OK status regardless of deletion.
     */


}