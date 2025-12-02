package com.aidan.learning_kotlin

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByOrderByCreatedAtDesc(): Iterable<Article>
}

// STUDY how can I have things imported automatically as I type them in IntelliJ?
// STUDY what is findAllByOrderByCreatedAtDesc?
// STUDY why do I need to import Article if it's in the same package?