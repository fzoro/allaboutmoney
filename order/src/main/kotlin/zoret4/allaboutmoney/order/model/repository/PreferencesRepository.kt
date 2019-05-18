package zoret4.allaboutmoney.order.model.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import zoret4.allaboutmoney.order.model.domain.Preferences

@Repository
interface PreferencesRepository : CrudRepository<Preferences, String> {
    fun deleteByVendor_Name(name: String)
    fun findByVendor_Name(name: String) : Preferences?
}