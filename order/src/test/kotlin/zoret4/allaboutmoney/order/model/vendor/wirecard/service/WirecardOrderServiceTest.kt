package zoret4.allaboutmoney.order.model.vendor.wirecard.service

import br.com.moip.API
import br.com.moip.api.OrderAPI
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.eq
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.model.domain.factory.OrderTestFactory
import br.com.moip.resource.Order as WirecardOrder


class WirecardOrderServiceTest {
    lateinit var service: WirecardOrderService
    @Mock
    lateinit var props: AppProperties
    @Mock
    lateinit var objectMapper: ObjectMapper
    @Mock
    lateinit var api: API
    @Mock
    lateinit var orderAPI: OrderAPI
    @Mock
    lateinit var objectWriter: ObjectWriter

    private lateinit var wOrder: WirecardOrder

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        service = WirecardOrderService(props, objectMapper, api)

        wOrder = Mockito.mock(WirecardOrder::class.java)
        given(api.order()).willReturn(orderAPI)
        given(orderAPI.get(eq("valid_id"))).willReturn(wOrder)
        given(objectMapper.writerWithDefaultPrettyPrinter()).willReturn(objectWriter)
        given(objectWriter.writeValueAsString(eq(wOrder))).willReturn("json")
    }

    @Test
    fun `given valid id, it should return an wirecard order`() {
        assertEquals("json", service.getOrder("valid_id"))
    }

    @Test
    fun `given an invalid id, it should throw a resource not found exception`() {
        assertThrows(ResourceNotFoundException::class.java) { service.getOrder("not_found_id") }
    }

    @Test
    fun `order request must be build correctly`() {

        //service.checkoutByVendor("vendor_customer_id", OrderTestFactory.simple())

    }

}
