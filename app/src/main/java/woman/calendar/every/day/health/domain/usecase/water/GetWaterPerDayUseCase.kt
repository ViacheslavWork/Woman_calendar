package woman.calendar.every.day.health.domain.usecase.water

import woman.calendar.every.day.health.utils.Constants


class GetWaterPerDayUseCase {
    fun execute(weight: Int?): Float {
        if (weight == null) return Constants.AVERAGE_AMOUNT_OF_WATER_PER_DAY
        return Constants.WATER_L_PER_KG * weight
    }
}