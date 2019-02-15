package zoret4.allaboutmoney.order

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import zoret4.allaboutmoney.order.configuration.props.AppProperties

@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
class OrderApplication

fun main(args: Array<String>) {
    runApplication<OrderApplication>(*args)
}

@Bean
fun objectMapper(): ObjectMapper {
    return ObjectMapper()
}