package com.natural.cycles.period.tracker.domain.usecase.water

import com.natural.cycles.period.tracker.utils.Constants
import java.util.*

class GetWaterPerDayUseCase {
    fun execute(weight: Int?): Float {
        if (weight == null) return Constants.AVERAGE_AMOUNT_OF_WATER_PER_DAY
        return when (Locale.getDefault().country) {
            "US" -> Constants.WATER_L_PER_KG * weight / 2.2f
            else -> Constants.WATER_L_PER_KG * weight
        }
    }
}