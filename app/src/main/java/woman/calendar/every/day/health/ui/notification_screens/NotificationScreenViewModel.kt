package woman.calendar.every.day.health.ui.notification_screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.DailyNotificationData
import woman.calendar.every.day.health.domain.usecase.GetDailyNotificationDataUseCase
import woman.calendar.every.day.health.domain.usecase.cycles.GetLastCyclesUseCase

class NotificationScreenViewModel(
    private val getDailyNotificationDataUseCase: GetDailyNotificationDataUseCase,
    private val getLastCyclesUseCase: GetLastCyclesUseCase
) : ViewModel() {
    private val _notificationData = MutableLiveData<DailyNotificationData?>()
    val notificationData: LiveData<DailyNotificationData?> = _notificationData

    private val _lengthOfCycle = MutableLiveData<Int>()
    val lengthOfCycle: LiveData<Int> = _lengthOfCycle

    fun init(date: LocalDate) {
        viewModelScope.launch {
            _notificationData.postValue(getDailyNotificationDataUseCase.execute(date))
            if (getLastCyclesUseCase.execute(1).isNotEmpty()) {
                _lengthOfCycle.postValue(
                    getLastCyclesUseCase.execute(1)[0].getDaysAfterStartOfPeriod()
                )
            }
        }
    }
}