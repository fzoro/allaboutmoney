package zoret4.allaboutmoney.order.configuration.initializers

import br.com.moip.API
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.model.vendor.wirecard.service.WirecardPreferencesService

@Component
class WirecardPreferencesInit(private val preferencesService: WirecardPreferencesService) {


    companion object {
        val LOG = logger()
    }


    @EventListener
    fun onStart(event: ContextRefreshedEvent) {
        preferencesService.syncPreferences()
    }
}