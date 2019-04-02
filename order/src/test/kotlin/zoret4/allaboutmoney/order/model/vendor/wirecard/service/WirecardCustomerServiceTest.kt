package zoret4.allaboutmoney.order.model.vendor.wirecard.service

import br.com.moip.API
import br.com.moip.api.CustomerAPI
import br.com.moip.request.CustomerRequest
import br.com.moip.resource.Customer
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
import zoret4.allaboutmoney.order.model.domain.factory.CustomerTestFactory


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

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        service = WirecardCustomerService(props, objectMapper, api)
    }

    @Test
    fun `customer request must be build correctly`() {
        val simpleCustomer = CustomerTestFactory.simple()
        val wirecardCustomer = Customer()
        given(api.customer()).willReturn(customerAPI)
        given(customerAPI.create(any())).willReturn(wirecardCustomer)
        given(objectMapper.writerWithDefaultPrettyPrinter()).willReturn(objectWriter)
        given(objectWriter.writeValueAsString(eq(wirecardCustomer))).willReturn("json")


        service.postCustomer(simpleCustomer)

        val argument = ArgumentCaptor.forClass(CustomerRequest::class.java)
        verify(customerAPI).create(argument.capture())
        assertEquals(simpleCustomer.email, argument.value.email)
        assertEquals(simpleCustomer.email, argument.value.email)
        with(argument.value) {
            assertAll("customer request preparation",
                    { assertEquals(simpleCustomer.id, ownId) },
                    { assertEquals(simpleCustomer.fullName, fullname) },
                    { assertEquals(simpleCustomer.email, email) },
                    { assertEquals(simpleCustomer.birthDate.toDate().time, birthDate.date.time) },
                    { assertEquals(simpleCustomer.taxonomyId, taxDocument.number) },
                    { assertEquals(simpleCustomer.taxonomyType.toString(), taxDocument.type.toString()) },
                    { assertEquals(simpleCustomer.phoneNumber, phone.number) },
                    { assertEquals(simpleCustomer.address.city, shippingAddress.city) },
                    { assertEquals(simpleCustomer.address.state, shippingAddress.state) },
                    { assertEquals(simpleCustomer.address.street, shippingAddress.street) },
                    { assertEquals(simpleCustomer.address.streetNumber, shippingAddress.streetNumber) },
                    { assertEquals(simpleCustomer.address.district, shippingAddress.district) }

            )
        }
    }
}
