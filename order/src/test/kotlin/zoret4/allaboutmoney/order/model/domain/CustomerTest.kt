package zoret4.allaboutmoney.order.model.domain

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.RETURNS_DEEP_STUBS

@RunWith(PowerMockRunner.class)
@PrepareForTest(ClassWithFinal.class)
class CustomerTest {

    @Test
    fun `customer is considered full if there is email`(){
        val customer = Mockito.mock(Customer::class.java, RETURNS_DEEP_STUBS)
        println(customer)
    }

    fun `customer is NOT full when email is empty`(){

    }
}