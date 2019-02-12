package zoret4.allaboutmoney.order.web.resource

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import zoret4.allaboutmoney.order.model.domain.factory.CustomerTestFactory
import zoret4.allaboutmoney.order.model.service.contracts.CustomerService
import zoret4.allaboutmoney.order.util.TestsHelper.Companion.any
import zoret4.allaboutmoney.order.web.resources.CustomerResource

@ExtendWith(SpringExtension::class)
@WebMvcTest(CustomerResource::class)
class CustomerResourceTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var customerService: CustomerService

    private val baseUri = "/customers"

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `customer not found = 404`() {
        mvc.perform(get("$baseUri/some-id"))
                .andExpect(status().isNotFound)

    }

    @Test
    fun `customer found = 200`() {
        val expectedCustomer = CustomerTestFactory.simple()
        given(customerService.get(any())).willReturn(expectedCustomer)
        mvc.perform(get("$baseUri/some-id"))
                .andExpect(status().isOk)
    }
}