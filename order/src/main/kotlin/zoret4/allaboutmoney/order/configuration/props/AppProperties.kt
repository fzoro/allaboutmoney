package zoret4.allaboutmoney.order.configuration.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
class AppProperties {
    val upstream = Upstream()

    class Upstream {
        val wirecard = Wirecard()

        class Wirecard {
            lateinit var uri: String
            lateinit var token: String
            lateinit var key: String
        }
    }

}