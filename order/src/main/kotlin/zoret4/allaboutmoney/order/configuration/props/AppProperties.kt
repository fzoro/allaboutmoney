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
            val preferences = Preferences()

            class Preferences {
                lateinit var notifications: List<Notifications>

                class Notifications {
                    lateinit var target: String
                    lateinit var events: List<String>
                }
            }

        }
    }

}