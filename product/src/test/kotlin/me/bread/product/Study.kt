package me.bread.product

import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Study {

    @Test
    fun execute() {
        // 테스트 문서 수
        val documentCount = 50_000
        println("생성 중: $documentCount 문서...")

        val documents = generateDocuments(documentCount)
        println("문서 생성 완료!\n")

        // 인덱스 초기화
        val rdsIndex = RdsStyleBTreeIndex()
        val esIndex = ElasticsearchStyleInvertedIndex()

        // 인덱스 구축 시간 측정
        println("=== 인덱스 구축 시간 비교 ===")

        val rdsIndexingTime = measureTimeMillis {
            documents.forEach { rdsIndex.indexDocument(it) }
        }
        println("RDS 스타일 인덱스 구축: ${rdsIndexingTime}ms")

        val esIndexingTime = measureTimeMillis {
            documents.forEach { esIndex.indexDocument(it) }
        }
        println("Elasticsearch 스타일 인덱스 구축: ${esIndexingTime}ms")
        println()

        // 단일 단어 검색 테스트
        println("=== 단일 단어 검색 성능 비교 ===")
        val singleTerms = listOf("kotlin", "elasticsearch", "database", "performance")

        singleTerms.forEach { term ->
            println("검색어: \"$term\"")

            // RDS 스타일 검색 (인덱스 없이 전체 스캔)
            var rdsResults: List<Document>
            val rdsSearchTime = measureTimeMillis {
                rdsResults = rdsIndex.findTextInContent(term)
            }

            // Elasticsearch 스타일 검색 (역색인 활용)
            var esResults: List<Document>
            val esSearchTime = measureTimeMillis {
                esResults = esIndex.search(term)
            }

            println("RDS 스타일 검색 (전체 스캔): ${rdsResults.size}개 결과, 소요 시간: ${rdsSearchTime}ms")
            println("Elasticsearch 스타일 검색 (역색인): ${esResults.size}개 결과, 소요 시간: ${esSearchTime}ms")
            val speedup = if (esSearchTime > 0) rdsSearchTime.toFloat() / esSearchTime.toFloat() else "∞"
            println("속도 향상: ${speedup}배")
            println()
        }

        // 복합 검색어 테스트
        println("=== 복합 검색어 검색 성능 비교 ===")
        val complexQueries = listOf(
            listOf("kotlin", "programming"),
            listOf("search", "engine", "performance"),
            listOf("database", "indexing", "optimization")
        )

        complexQueries.forEach { terms ->
            val queryStr = terms.joinToString(" ")
            println("복합 검색어: \"$queryStr\"")

            // RDS 스타일 복합 검색
            var rdsResults: List<Document>
            val rdsSearchTime = measureTimeMillis {
                rdsResults = rdsIndex.findMultipleTermsInContent(terms)
            }

            // Elasticsearch 스타일 복합 검색
            var esResults: List<Document>
            val esSearchTime = measureTimeMillis {
                esResults = esIndex.searchMultipleTerms(terms)
            }

            println("RDS 스타일 검색 (전체 스캔): ${rdsResults.size}개 결과, 소요 시간: ${rdsSearchTime}ms")
            println("Elasticsearch 스타일 검색 (역색인): ${esResults.size}개 결과, 소요 시간: ${esSearchTime}ms")
            val speedup = if (esSearchTime > 0) rdsSearchTime.toFloat() / esSearchTime.toFloat() else "∞"
            println("속도 향상: ${speedup}배")
            println()
        }

        // 스케일링 테스트 - 다양한 문서 수에 따른 검색 시간
        println("=== 검색 성능 스케일링 테스트 ===")
        println("문서 수에 따른 'elasticsearch' 단어 검색 시간 비교")

        val testSizes = listOf(1_000, 5_000, 10_000, 20_000, 50_000)
        println("문서 수\tRDS 검색(ms)\tES 검색(ms)\t속도 향상(배)")

        testSizes.forEach { size ->
            val testDocs = documents.take(size)

            // 테스트용 작은 인덱스 생성
            val smallRdsIndex = RdsStyleBTreeIndex()
            val smallEsIndex = ElasticsearchStyleInvertedIndex()

            testDocs.forEach {
                smallRdsIndex.indexDocument(it)
                smallEsIndex.indexDocument(it)
            }

            // RDS 스타일 검색
            val rdsTime = measureTimeMillis {
                smallRdsIndex.findTextInContent("elasticsearch")
            }

            // Elasticsearch 스타일 검색
            val esTime = measureTimeMillis {
                smallEsIndex.search("elasticsearch")
            }

            val speedup = if (esTime > 0) rdsTime.toFloat() / esTime.toFloat() else "∞"
            println("$size\t$rdsTime\t$esTime\t$speedup")
        }
    }
}

/**
 * 문서 클래스: ID, 제목, 내용을 포함
 */
data class Document(
    val id: Int,
    val title: String,
    val content: String
)

/**
 * RDS 스타일의 B-Tree 인덱스 구현
 * - 실제 B-Tree 구현은 복잡하므로 핵심 원리만 시뮬레이션합니다
 */
class RdsStyleBTreeIndex {
    // 컬럼 값 -> 문서 ID 목록 (B-Tree는 실제로 이보다 복잡하지만 개념적으로 이렇게 동작)
    private val columnIndex = sortedMapOf<String, MutableList<Int>>()

    // 문서 ID -> 문서 매핑
    private val documents = mutableMapOf<Int, Document>()

    // 인덱스가 걸린 컬럼(예: id, title)을 위한 인덱스 구축
    fun indexDocument(document: Document) {
        documents[document.id] = document

        // ID 컬럼 인덱싱
        columnIndex.getOrPut(document.id.toString()) { mutableListOf() }.add(document.id)

        // 제목 컬럼 인덱싱
        columnIndex.getOrPut(document.title) { mutableListOf() }.add(document.id)
    }

    // 정확한 일치 검색 (B-Tree 인덱스 활용)
    fun findExactMatch(columnValue: String): List<Document> {
        val docIds = columnIndex[columnValue] ?: return emptyList()
        return docIds.mapNotNull { documents[it] }
    }

    // 텍스트 내용 검색 (인덱스 없이 전체 스캔)
    fun findTextInContent(searchTerm: String): List<Document> {
        // RDS에서 인덱스 없는 LIKE '%searchTerm%' 쿼리와 유사
        return documents.values.filter { doc ->
            doc.content.contains(searchTerm, ignoreCase = true)
        }
    }

    // 여러 텍스트 검색어 검색 (전체 스캔)
    fun findMultipleTermsInContent(searchTerms: List<String>): List<Document> {
        return documents.values.filter { doc ->
            searchTerms.all { term ->
                doc.content.contains(term, ignoreCase = true)
            }
        }
    }
}

/**
 * Elasticsearch 스타일의 역색인 구현
 */
class ElasticsearchStyleInvertedIndex {
    // 역색인: 단어 -> 문서 ID 집합
    // 해시 테이블 구조로 O(1) 시간 복잡도로 접근
    private val invertedIndex = mutableMapOf<String, MutableSet<Int>>()

    // 문서 저장소
    private val documents = mutableMapOf<Int, Document>()

    // 문서 인덱싱 - 내용 분석 및 역색인 구축
    fun indexDocument(document: Document) {
        documents[document.id] = document

        // 내용을 단어로 분리 (Elasticsearch의 분석기 역할)
        val words = document.content
            .split(Regex("\\s+|\\p{Punct}"))
            .map { it.lowercase() }
            .filter { it.isNotEmpty() }

        // 각 단어를 역색인에 추가
        words.forEach { word ->
            invertedIndex.getOrPut(word) { mutableSetOf() }.add(document.id)
        }
    }

    // 단일 단어 검색 (역색인 활용)
    fun search(term: String): List<Document> {
        val searchTerm = term.lowercase()
        val docIds = invertedIndex[searchTerm] ?: emptySet()
        return docIds.mapNotNull { documents[it] }
    }

    // 복합 검색어 검색 (AND 조건)
    fun searchMultipleTerms(terms: List<String>): List<Document> {
        if (terms.isEmpty()) return emptyList()

        // 첫 번째 검색어 결과
        var resultIds = invertedIndex[terms[0].lowercase()] ?: return emptyList()

        // 각 추가 검색어로 결과 집합 필터링 (교집합)
        for (i in 1 until terms.size) {
            val termDocIds = invertedIndex[terms[i].lowercase()] ?: emptySet()
            resultIds = resultIds.intersect(termDocIds).toMutableSet()

            // 교집합이 비어있으면 일찍 종료
            if (resultIds.isEmpty()) return emptyList()
        }

        return resultIds.mapNotNull { documents[it] }
    }
}

/**
 * 테스트용 문서 생성
 */
fun generateDocuments(count: Int): List<Document> {
    val topics = listOf(
        "Kotlin is a modern programming language that makes developers happier",
        "Java virtual machine provides a platform-independent execution environment",
        "Elasticsearch is a distributed search and analytics engine",
        "PostgreSQL is an advanced open-source relational database",
        "MongoDB is a popular NoSQL database for modern applications",
        "Spring Framework is the most popular application framework for Java",
        "Android development uses Kotlin as the preferred language",
        "Database indexing is crucial for query performance optimization",
        "Search engines use inverted indices for efficient text searching",
        "RDS systems primarily use B-Tree indexes for structured data"
    )

    val extraWords = listOf(
        "performance", "optimization", "development", "implementation",
        "analysis", "design", "structure", "pattern", "functionality",
        "interface", "component", "module", "service", "architecture"
    )

    return (1..count).map { id ->
        val baseTopic = topics.random()
        val extras = List(5) { extraWords.random() }.joinToString(" ")

        Document(
            id = id,
            title = "Document $id",
            content = "$baseTopic. Additional keywords: $extras."
        )
    }
}
