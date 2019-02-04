package zoret4.allaboutmoney.order.web.resource

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import zoret4.allaboutmoney.order.model.domain.Address
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.domain.TaxonomyType
import zoret4.allaboutmoney.order.model.domain.Tracer
import zoret4.allaboutmoney.order.model.service.contracts.CustomerService
import zoret4.allaboutmoney.order.util.TestsHelper.Companion.any
import java.time.LocalDate

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderResourceTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @MockBean
    lateinit var customerService: CustomerService

    private val baseUri = "/customers"


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `customer not found = 404`() {
        val result = testRestTemplate.getForEntity("$baseUri/some-id", String::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun `customer found = 200`() {

        val expectedCustomer = Customer(
                id = "1",
                vendorId = "1",
                fullName = "1",
                email = "1",
                birthDate = LocalDate.now(),
                taxonomyId = "1",
                taxonomyType = TaxonomyType.CPF,
                phoneNumber = "1",
                address = Address(
                        street = "address",
                        streetNumber = "address",
                        complement = "address",
                        city = "address",
                        state = "address",
                        district = "address",
                        country = "address",
                        zipCode = "address"
                ),
                tracer = Tracer(requestId = "1", createdBy = "zoret4", origin = "unit test")
        )

        given(customerService.get(any())).willReturn(expectedCustomer)

        val result = testRestTemplate.getForEntity("$baseUri/some-id", String::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }
}