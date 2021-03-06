package feign.gateways.library

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import utils.SimpleWireMockExtension

@ExtendWith(SimpleWireMockExtension::class)
@SpringBootTest(classes = [LibraryConfiguration::class])
internal class LibraryAccessorIntegrationTest(
    @Autowired val settings: LibrarySettings,
    @Autowired val cut: LibraryAccessor
) {

    @BeforeEach fun setDynamicUrl(wireMock: WireMockServer) {
        settings.url = "http://localhost:${wireMock.port()}"
    }

    @Test fun `adding a book returns true if library service responds positively`(wireMock: WireMockServer) {
        wireMock.givenThat(
            post(urlEqualTo("/api/books"))
                .withHeader(CONTENT_TYPE, equalTo(APPLICATION_JSON_VALUE))
                .withRequestBody(equalToJson("""{ "title": "Clean Code", "isbn": "9780132350884" }"""))
                .willReturn(aResponse().withStatus(200))
        )

        assertThat(cut.addBook("Clean Code", "9780132350884")).isTrue()
    }

    @Test fun `adding a book returns false if library service responds negatively`(wireMock: WireMockServer) {
        wireMock.givenThat(
            post(urlEqualTo("/api/books"))
                .willReturn(aResponse().withStatus(500))
        )

        assertThat(cut.addBook("Clean Code", "9780132350884")).isFalse()
    }

}
