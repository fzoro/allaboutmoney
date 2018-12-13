package zoret4.allaboutmoney.order

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
class AppProperties {
    lateinit var test: String
}