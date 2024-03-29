package com.natural.cycles.period.tracker.domain

import com.natural.cycles.period.tracker.domain.model.DailyNotificationData

interface DailyNotificationDataProvider {
    fun getPeriodStartData(): DailyNotificationData
    fun getPeriodMidData(): DailyNotificationData
    fun getPeriodEndData(): DailyNotificationData
    fun getPostPeriodData(): DailyNotificationData
    fun getFertileStartData(): DailyNotificationData
    fun getFertileMidData(): DailyNotificationData
    fun getOvulationDayData(): DailyNotificationData
    fun getFertileEndData(): DailyNotificationData
    fun getPostFertileStartData(): DailyNotificationData
    fun getPostFertileMidData(): DailyNotificationData
    fun getPostFertileEndData(): DailyNotificationData
    fun getPrePeriodStartData(): DailyNotificationData
    fun getPrePeriodMidData(): DailyNotificationData
    fun getPrePeriodEndData(): DailyNotificationData
}