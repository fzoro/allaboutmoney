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
import zoret4.allaboutmoney.order.model.repository.PreferencesRepository
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

    @Mock
    lateinit var preferencesRepository: PreferencesRepository

    private lateinit var wOrder: WirecardOrder

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        service = WirecardOrderService(props, objectMapper, api,preferencesRepository)

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
//        override fun getOrder(orderId: String)
//        override fun checkoutByVendor(vendorCustomerId: String, order: Order): String {
//        val simpleCustomer = CustomerTestFactory.simple()
//        val wirecardCustomer = Customer()
//        given(api.customer()).willReturn(customerAPI)
//        given(customerAPI.create(any())).willReturn(wirecardCustomer)
//        given(objectMapper.writerWithDefaultPrettyPrinter()).willReturn(objectWriter)
//        given(objectWriter.writeValueAsString(eq(wirecardCustomer))).willReturn("json")
//
//
//        service.postCustomer(simpleCustomer)
//
//        val argument = ArgumentCaptor.forClass(CustomerRequest::class.java)
//        verify(customerAPI).create(argument.capture())
//        assertEquals(simpleCustomer.email, argument.value.email)
//        assertEquals(simpleCustomer.email, argument.value.email)
//        with(argument.value) {
//            assertAll("customer request preparation",
//                    { assertEquals(simpleCustomer.id, ownId) },
//                    { assertEquals(simpleCustomer.fullName, fullname) },
//                    { assertEquals(simpleCustomer.email, email) },
//                    { assertEquals(simpleCustomer.birthDate.toDate().time, birthDate.date.time) },
//                    { assertEquals(simpleCustomer.taxonomyId, taxDocument.number) },
//                    { assertEquals(simpleCustomer.taxonomyType.toString(), taxDocument.type.toString()) },
//                    { assertEquals(simpleCustomer.phoneNumber, phone.number) },
//                    { assertEquals(simpleCustomer.address.city, shippingAddress.city) },
//                    { assertEquals(simpleCustomer.address.state, shippingAddress.state) },
//                    { assertEquals(simpleCustomer.address.street, shippingAddress.street) },
//                    { assertEquals(simpleCustomer.address.streetNumber, shippingAddress.streetNumber) },
//                    { assertEquals(simpleCustomer.address.district, shippingAddress.district) }
//
//            )
//        }
    }

}
