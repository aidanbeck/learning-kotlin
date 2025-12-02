package com.aidan.learning_kotlin

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/articles") // STUDY why is this named api/v1/articles? is this a standard?
class ArticleController (val repository: ArticleRepository) {

    /*
        // dummy data, replaced with db access
        val articles = mutableListOf( Article( "My Title", content = "My Content") )

        Notes:

        parameters be defined implicitly by order or explicitly by "name = value"

        values that are nullable or have defaults do not need to be passed

        the "New" keyword is no longer necessary, just call the class.

     */

    @GetMapping
    fun articles() = repository.findAllByOrderByCreatedAtDesc()
    /*
        originally `fun articles() = articles`
        Notes:

        "articles" value is inferred as the return. STUDY how does this this work? Does it just return the value it equals?

        The function return type MutableList<Article> is also inferred.

        They can also be specified like so:

        fun articles() : MutableList<Article> {
            return articles
        }
    */

    @GetMapping("/{slug}")
    fun articles(@PathVariable slug: String) =
        articles().find{ article -> article.slug == slug } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    /*
        originally `articles.find { article -> article.slug == slug } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)`

        The tutorial uses a "findBySlug" method on repository itself, but does not show it or link to it.
        So I am calling it on the "articles" method, specifically the one defined with no parameters.

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
    fun addArticle (@RequestBody article: Article):Article {
        repository.save(Article(null,article.title, article.content) )
        return article
    }
    /*
        original:
        articles.add(Article(article.title, article.content))
        return article
        STUDY why is id set to null? does saving a null id set it to the next available id?

        Notes:

        The return type must be defined in a more complicated function like this. STUDY When must it be defined, and when can it be inferred?

        Remember to return the article in a post request

        In order to allow post requests to submit without all fields (like createdAt or slug) I:
        - made the non-required fields nullable.
        - loaded the required fields into a constructor, so that the non-required fields will be set to their default calculated values.
        The tutorial I am watching doesn't do these things, but it still works for them. STUDY Why is this the case?
     */

    @PutMapping("/{slug}")
    fun updateArticle (@RequestBody article: Article, @PathVariable slug: String): Article {
        val existingArticle = articles().find { it.slug == slug } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        existingArticle.content = article.content
        repository.save(article)
        return article
    }
    /*
        original:
        val existingArticle = articles.find { it.slug == slug } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        existingArticle.content = article.content
        return article

        STUDY does this mean updating articles need to have their correct id's?

        Notes:

        Put Requests should have a /{slug} identifier instead of just an object, so that the object can be sent to the right place.

        uses "it" instead of "article" to avoid confusion with the input article. STUDY is using "it" standard practice?
        ANSWER "it" is a keyword for the single parameter in a lambda function. By using "it", we are targeting each element in the list.

        This function only updates the content. Other methods will need to be used
        It also only returns the original article, which can be different than the updated article STUDY is this truly best practice?
     */

    @DeleteMapping("/{slug}")
    fun deleteArticle(@PathVariable slug: String) {
        val existingArticle = articles().find { article -> article.slug == slug } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        repository.delete(existingArticle)
    }
    /*
        original: articles.removeIf { article -> article.slug == slug }
        TODO note that the exception was added!
        Notes:

        .removeIf() compares slugs for each element, and removes the element with a true result

        STUDY What does this return? Why doesn't this need an exception for if a slug is not found?
        HALF-ANSWER: It sends back a 200 OK status regardless of deletion.
     */


}