package zoret4.allaboutmoney.order.configuration

import br.com.moip.resource.Customer
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


@Configuration
class ParserConfig {

    // no needed if fix is applied: https://github.com/wirecardBrasil/moip-sdk-java-le/pull/131
    @Bean
    fun jacksonBuilder(): Jackson2ObjectMapperBuilder {
        abstract class CustomerMixin {
            @JsonIgnore
            abstract fun getMoipAccountId(): String
        }

        val b = Jackson2ObjectMapperBuilder()
        b.indentOutput(true).mixIn(Customer::class.java, CustomerMixin::class.java)
        b.serializationInclusion(JsonInclude.Include.NON_NULL)
        return b
    }
}