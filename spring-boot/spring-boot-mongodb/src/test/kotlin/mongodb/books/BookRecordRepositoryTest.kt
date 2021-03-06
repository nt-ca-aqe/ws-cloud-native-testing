package mongodb.books

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import java.util.UUID

@DataMongoTest
internal class BookRecordRepositoryTest(
    @Autowired val cut: BookRecordRepository
) {

    @BeforeEach fun clearDatabase() = cut.deleteAll()

    @Test fun `document can be saved`() {
        val id = UUID.randomUUID()
        val document = BookRecordDocument(id, "Clean Code", "9780132350884")
        val savedEntity = cut.save(document)
        assertThat(savedEntity).isEqualTo(document)
    }

    @Test fun `document can be found by id`() {
        val id = UUID.randomUUID()
        val savedDocument = cut.save(BookRecordDocument(id, "Clean Code", "9780132350884"))
        val foundDocument = cut.findById(id)
        assertThat(foundDocument).hasValue(savedDocument)
    }

    @Test fun `document can be found by title`() {
        val d1 = cut.save(BookRecordDocument(UUID.randomUUID(), "Clean Code", "9780132350884"))
        val d2 = cut.save(BookRecordDocument(UUID.randomUUID(), "Clean Architecture", "9780134494166"))
        val d3 = cut.save(BookRecordDocument(UUID.randomUUID(), "Clean Code", "9780132350884"))
        val foundDocuments = cut.findByTitle("Clean Code")
        assertThat(foundDocuments)
            .contains(d1, d3)
            .doesNotContain(d2)
    }

}
