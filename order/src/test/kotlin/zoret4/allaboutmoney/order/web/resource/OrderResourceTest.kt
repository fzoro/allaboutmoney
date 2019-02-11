package zoret4.allaboutmoney.order.web.resource

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import zoret4.allaboutmoney.order.model.domain.Address
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.domain.TaxonomyType
import zoret4.allaboutmoney.order.model.domain.Tracer
import zoret4.allaboutmoney.order.model.service.contracts.OrderService
import zoret4.allaboutmoney.order.util.TestsHelper.Companion.any
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderResourceTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @MockBean
    lateinit var orderService: OrderService

    private val baseUri = "/orders"

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `order not found = 404`() {
        val result = testRestTemplate.getForEntity("$baseUri/some-id", String::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun `order found = 200`() {

    }
}