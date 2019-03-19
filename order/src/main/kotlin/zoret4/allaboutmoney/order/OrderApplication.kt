package zoret4.allaboutmoney.order

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import zoret4.allaboutmoney.order.configuration.props.AppProperties


@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
class OrderApplication

fun main(args: Array<String>) {

    runApplication<OrderApplication>(*args)
}