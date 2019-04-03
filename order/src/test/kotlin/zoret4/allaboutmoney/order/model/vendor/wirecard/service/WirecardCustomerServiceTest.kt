package zoret4.allaboutmoney.order.model.vendor.wirecard.service

import br.com.moip.API
import br.com.moip.api.CustomerAPI
import br.com.moip.request.CustomerRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.configuration.toDate
import zoret4.allaboutmoney.order.model.domain.Customer
import zoret4.allaboutmoney.order.model.domain.factory.CustomerTestFactory
import br.com.moip.resource.Customer as WirecardCustomer


class WirecardCustomerServiceTest {
    lateinit var service: WirecardCustomerService
    @Mock
    lateinit var props: AppProperties
    @Mock
    lateinit var objectMapper: ObjectMapper
    @Mock
    lateinit var api: API
    @Mock
    lateinit var customerAPI: CustomerAPI

    @Mock
    lateinit var objectWriter: ObjectWriter

    private lateinit var customer: Customer
    private lateinit var wCustomer: WirecardCustomer

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        service = WirecardCustomerService(props, objectMapper, api)
        customer = CustomerTestFactory.simple()
        wCustomer = mock(WirecardCustomer::class.java)
        given(api.customer()).willReturn(customerAPI)
        given(customerAPI.create(any())).willReturn(wCustomer)
        given(objectMapper.writerWithDefaultPrettyPrinter()).willReturn(objectWriter)
        given(objectWriter.writeValueAsString(eq(wCustomer))).willReturn("json")

    }

    @Test
    fun `customer request must be build correctly`() {

        service.postCustomer(customer)

        val argument = ArgumentCaptor.forClass(CustomerRequest::class.java)
        verify(customerAPI).create(argument.capture())
        assertEquals(customer.email, argument.value.email)
        assertEquals(customer.email, argument.value.email)
        with(argument.value) {
            assertAll("customer request preparation",
                    { assertEquals(customer.id, ownId) },
                    { assertEquals(customer.fullName, fullname) },
                    { assertEquals(customer.email, email) },
                    { assertEquals(customer.birthDate.toDate().time, birthDate.date.time) },
                    { assertEquals(customer.taxonomyId, taxDocument.number) },
                    { assertEquals(customer.taxonomyType.toString(), taxDocument.type.toString()) },
                    { assertEquals(customer.phoneNumber, phone.number) },
                    { assertEquals(customer.address.city, shippingAddress.city) },
                    { assertEquals(customer.address.state, shippingAddress.state) },
                    { assertEquals(customer.address.street, shippingAddress.street) },
                    { assertEquals(customer.address.streetNumber, shippingAddress.streetNumber) },
                    { assertEquals(customer.address.district, shippingAddress.district) }

            )
        }
    }
}
