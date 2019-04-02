package zoret4.allaboutmoney.order.model.vendor.wirecard.configuration

import br.com.moip.API
import br.com.moip.Client
import br.com.moip.authentication.BasicAuth
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import zoret4.allaboutmoney.order.configuration.props.AppProperties


@Configuration
class WirecardConfig(private val props: AppProperties) {

    @Bean
    fun api(): API {
        val auth = BasicAuth(props.upstream.wirecard.token, props.upstream.wirecard.key)
        val client = Client(Client.SANDBOX, auth)
        return API(client)
    }
}