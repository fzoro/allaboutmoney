package zoret4.allaboutmoney.order.model.vendor.wirecard.service

import br.com.moip.API
import br.com.moip.request.NotificationPreferenceRequest
import br.com.moip.resource.NotificationPreference
import br.com.moip.response.NotificationPreferenceListResponse
import org.springframework.stereotype.Service
import zoret4.allaboutmoney.order.configuration.logger
import zoret4.allaboutmoney.order.configuration.props.AppProperties
import zoret4.allaboutmoney.order.model.service.contracts.vendor.VendorPreferencesService


@Service
class WirecardPreferencesService(private val props: AppProperties,
                                 private val api: API) : VendorPreferencesService {

    companion object {
        val LOG = logger()
    }


    // it always wipe out the existent events and create them again
    fun syncPreferences() {
        wipeOutNotificationPreferences()
        props.upstream.wirecard.preferences.notifications
                .map { notification ->
                    val req = NotificationPreferenceRequest()
                    req.target(notification.target)
                    notification.events.forEach { req.addEvent(it) }
                    req
                }
                .forEach { create(it) }
    }

    fun wipeOutNotificationPreferences() {
        list()?.forEach {
            LOG.warn("removing notification_preference={}", it)
            delete(it.id)
        }
    }

    fun create(notificationRequest: NotificationPreferenceRequest) {
        LOG.debug("creating(POST) notification.request={}", notificationRequest)
        api.notification().create(notificationRequest)
    }

    fun get(id: String): NotificationPreference? = api.notification().get(id)
    fun list(): NotificationPreferenceListResponse? = api.notification().list()
    fun delete(id: String): Boolean = api.notification().delete(id)
}