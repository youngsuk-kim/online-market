package me.bread.product

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import java.time.LocalDate
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: ElasticsearchRepository<Book, String>


@Document(indexName = "books")
class Book(
    @Id
    var id: String? = null,
    var name: String,
    var author: String,
    var release_date: LocalDate,
    var page_count: Long
) {
    override fun toString(): String {
        return "Book(name='$name', author='$author', release_date=$release_date, page_count=$page_count)"
    }
}


@SpringBootTest
class ProductApplicationTests {

    @Autowired
    lateinit var es: ElasticsearchOperations

    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun contextLoads() {
        println(es.get("hUKgpJYBWjY6ncv5a5B1", Book::class.java))

        println(bookRepository.findAll())
    }
}
