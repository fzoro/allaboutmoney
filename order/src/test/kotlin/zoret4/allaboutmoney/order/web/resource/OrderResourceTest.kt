package zoret4.allaboutmoney.order.web.resource

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import zoret4.allaboutmoney.order.configuration.toJsonWithMapper
import zoret4.allaboutmoney.order.model.domain.Order
import zoret4.allaboutmoney.order.model.domain.factory.OrderTestFactory
import zoret4.allaboutmoney.order.model.service.contracts.OrderService
import zoret4.allaboutmoney.order.util.TestsHelper.Companion.any
import zoret4.allaboutmoney.order.web.resources.OrderResource


@ExtendWith(SpringExtension::class)
@WebMvcTest(OrderResource::class)
class OrderResourceTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var orderService: OrderService

    private val baseUri = "/orders"

    @Test
    fun `order found = 200`() {
        given(orderService.get(anyString())).willReturn(Mockito.mock(Order::class.java))
        mvc.perform(MockMvcRequestBuilders.get("$baseUri/1")).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `order not found = 404`() {
        given(orderService.get(anyString())).willThrow(ResourceNotFoundException())
        mvc.perform(MockMvcRequestBuilders.get("$baseUri/1")).andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `post for creation  = 201`() {
        given(orderService.create(any())).willReturn("vendor link for payment")
        mvc.perform(MockMvcRequestBuilders
                .post("$baseUri")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OrderTestFactory.simple().toJsonWithMapper(ObjectMapper()))
        ).andExpect(MockMvcResultMatchers.status().isCreated)
    }
}