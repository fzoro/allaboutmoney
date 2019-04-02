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

    fun createNotificationPreference() {
        val notificationRequest = NotificationPreferenceRequest()
        with(props.upstream.wirecard.preferences.notifications) {
            notificationRequest.target(target)
            events.forEach { notificationRequest.addEvent(it) }
        }
        val notificationPreference = api.notification().create(notificationRequest)
    }

    fun wipeOutNotificationPreferences() {
        listNotificationPreference()?.forEach {
            LOG.warn("removing notification_preference={}", it)
            deleteNotificationPreference(it.id)
        }
    }

    fun getNotificationPreference(id: String): NotificationPreference? {
        return api.notification().get(id)
    }

    fun listNotificationPreference(): NotificationPreferenceListResponse? {
        return api.notification().list()
    }

    fun deleteNotificationPreference(id: String) {
        api.notification().delete(id)
    }
}