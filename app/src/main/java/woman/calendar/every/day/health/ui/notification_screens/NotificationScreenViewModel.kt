package woman.calendar.every.day.health.ui.notification_screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.DailyNotificationData
import woman.calendar.every.day.health.domain.usecase.GetDailyNotificationDataUseCase

class NotificationScreenViewModel(
    private val getDailyNotificationDataUseCase: GetDailyNotificationDataUseCase
) : ViewModel() {
    private val _notificationData = MutableLiveData<DailyNotificationData>()
    val notificationData: LiveData<DailyNotificationData> = _notificationData

    fun init(date: LocalDate) {
        _notificationData.postValue(getDailyNotificationDataUseCase.execute(date))
    }
}