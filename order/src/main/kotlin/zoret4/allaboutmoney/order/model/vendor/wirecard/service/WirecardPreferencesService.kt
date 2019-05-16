package zoret4.allaboutmoney.order.model.vendor.wirecard.service

import br.com.moip.API
import br.com.moip.request.NotificationPreferenceRequest
import br.com.moip.resource.NotificationPreference
import br.com.moip.response.NotificationPreferenceListResponse
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.model.domain.Preferences
import zoret4.allaboutmoney.order.model.domain.VendorPreferences
import zoret4.allaboutmoney.order.model.repository.PreferencesRepository
import zoret4.allaboutmoney.order.model.service.contracts.vendor.VendorPreferencesService


@Service
class WirecardPreferencesService(private val props: AppProperties,
                                 private val api: API,
                                 private val preferencesRepository: PreferencesRepository
) : VendorPreferencesService {

    companion object {
        val LOG = logger()
    }


    // it always wipe out the existent events and create them again
    fun syncPreferences() {
        wipeOutNotificationPreferences()
        LOG.warn("deleting preferences on database. (vendor_name=WIRECARD)")

        preferencesRepository.deleteByVendor_Name(props.upstream.wirecard.name)

        val events = props.upstream.wirecard.preferences.notifications
                .map { notification ->
                    val req = NotificationPreferenceRequest()
                    req.target(notification.target)
                    notification.events.forEach { req.addEvent(it) }
                    Pair(notification.name, req)
                }
                .map { preparedRequest ->
                    create(preparedRequest.second).run { Pair(preparedRequest.first, token) }
                }.toMap()

        val dbPreferences = Preferences(vendor = VendorPreferences(name = props.upstream.wirecard.name, eventTokens = events))
        LOG.debug("saving preference={} to the database", dbPreferences)
        preferencesRepository.save(dbPreferences)
    }


    fun wipeOutNotificationPreferences() {
        list()?.forEach {
            LOG.warn("removing notification_preference={}", it)
            delete(it.id)
        }
    }

    fun create(notificationRequest: NotificationPreferenceRequest): NotificationPreference {
        LOG.debug("creating(POST) notification.request={}", notificationRequest)
        return api.notification().create(notificationRequest)
    }

    fun get(id: String): NotificationPreference? = api.notification().get(id)
    fun list(): NotificationPreferenceListResponse? = api.notification().list()
    fun delete(id: String): Boolean = api.notification().delete(id)
}