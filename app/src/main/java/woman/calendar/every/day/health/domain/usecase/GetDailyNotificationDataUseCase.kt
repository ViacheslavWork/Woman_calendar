package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.DailyNotificationDataProvider
import woman.calendar.every.day.health.domain.model.DailyNotificationData

class GetDailyNotificationDataUseCase(private val getNotificationDataProvider: DailyNotificationDataProvider) {
    fun execute(date: LocalDate): DailyNotificationData {
        //TODO
        return getNotificationDataProvider.getOvulationDayData()
    }
}