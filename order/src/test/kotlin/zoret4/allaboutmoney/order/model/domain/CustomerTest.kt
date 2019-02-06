package zoret4.allaboutmoney.order.model.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.RETURNS_DEEP_STUBS
import zoret4.allaboutmoney.order.util.TestsHelper


//@RunWith(PowerMockRunner.class)
//@PrepareForTest(ClassWithFinal.class)
class CustomerTest {

    @Test
    fun `customer is considered full if there is email`(){
        Assertions.assertTrue(true)
        val customer = TestsHelper.mock<Customer>()
        given(customer.email).willReturn("AAA")
        given(customer.isFull()).willCallRealMethod()
        Customer.
        Assertions.assertTrue(customer.isFull())
    }

    fun `customer is NOT full when email is empty`(){

    }
}