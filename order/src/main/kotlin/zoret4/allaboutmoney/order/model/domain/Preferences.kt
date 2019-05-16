package zoret4.allaboutmoney.order.model.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document
data class Preferences(@Id val id: String = UUID.randomUUID().toString(),
                       val vendor: VendorPreferences)

data class VendorPreferences(val name: String, val eventTokens: Map<String, String>)
