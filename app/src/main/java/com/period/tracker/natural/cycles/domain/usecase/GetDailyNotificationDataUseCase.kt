package com.period.tracker.natural.cycles.domain.usecase

import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.DailyNotificationDataProvider
import com.period.tracker.natural.cycles.domain.model.DailyNotificationData
import com.period.tracker.natural.cycles.domain.model.DailyNotificationStatus.*
import com.period.tracker.natural.cycles.domain.usecase.cycles.GetLastCyclesUseCase

class GetDailyNotificationDataUseCase(
    private val getNotificationDataProvider: DailyNotificationDataProvider,
    private val getLastCyclesUseCase: GetLastCyclesUseCase
) {
    suspend fun execute(date: LocalDate): DailyNotificationData? {
        if (getLastCyclesUseCase.execute(1).isNotEmpty()) {
            val lastCycleStatus = getLastCyclesUseCase
                .execute(1)[0]
                .getDailyNotificationStatus(date)
            return when (lastCycleStatus) {
                PERIOD_START -> getNotificationDataProvider.getPeriodStartData()
                PERIOD_MID -> getNotificationDataProvider.getPeriodMidData()
                PERIOD_END -> getNotificationDataProvider.getPeriodEndData()
                POST_PERIOD -> getNotificationDataProvider.getPostPeriodData()
                FERTILE_START -> getNotificationDataProvider.getFertileStartData()
                FERTILE_MID -> getNotificationDataProvider.getFertileMidData()
                FERTILE_END -> getNotificationDataProvider.getFertileEndData()
                OVULATION_DAY -> getNotificationDataProvider.getOvulationDayData()
                POST_FERTILE_START -> getNotificationDataProvider.getPostFertileStartData()
                POST_FERTILE_MID -> getNotificationDataProvider.getPostFertileMidData()
                POST_FERTILE_END -> getNotificationDataProvider.getPostFertileEndData()
                PRE_PERIOD_START -> getNotificationDataProvider.getPrePeriodStartData()
                PRE_PERIOD_MID -> getNotificationDataProvider.getPrePeriodMidData()
                PRE_PERIOD_END -> getNotificationDataProvider.getPrePeriodEndData()
            }
        } else return null
    }
}