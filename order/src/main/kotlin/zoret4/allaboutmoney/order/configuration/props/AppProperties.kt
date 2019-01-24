package zoret4.allaboutmoney.order.configuration.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
class AppProperties {
    val services = Services()

    class Services {
        val external = External()

        class External {
            val wirecard = Wirecard()

            class Wirecard {
                lateinit var uri:String
                val resources = Resources()

                class Resources {
                    lateinit var customers: String
                    lateinit var orders: String
                    lateinit var payments: String
                }
            }
        }
    }

}