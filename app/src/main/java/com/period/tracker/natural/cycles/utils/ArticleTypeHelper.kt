package com.period.tracker.natural.cycles.utils

import com.period.tracker.natural.cycles.domain.model.ArticleType
import com.period.tracker.natural.cycles.domain.model.ArticleType.*

object ArticleTypeHelper {
    fun fromString(string: String?): ArticleType? {
        return when (string) {
            REPRODUCTIVE_HEALTH.name -> REPRODUCTIVE_HEALTH
            SEX.name -> SEX
            YOUR_CYCLE_PHASE.name -> YOUR_CYCLE_PHASE
            THE_LATEST.name -> THE_LATEST
            LGBTQ.name -> LGBTQ
            TIPS_FOR_FREE_PAIN.name -> TIPS_FOR_FREE_PAIN
            NUTRITION_AND_FITNESS.name -> NUTRITION_AND_FITNESS
            EARLY_SIGNS_OF_PREGNANCY.name -> EARLY_SIGNS_OF_PREGNANCY
            VAGINAL_DISCHARGE_COLOR.name -> VAGINAL_DISCHARGE_COLOR
            COVID.name -> COVID
            HOW_OVULATION_AFFECTS.name -> HOW_OVULATION_AFFECTS
            SEX_AND_YOUR_CYCLE.name -> SEX_AND_YOUR_CYCLE
            HOW_TO_MAKE_SEX_PAINLESS.name -> HOW_TO_MAKE_SEX_PAINLESS
            HOW_TO_GET_MORE_PLEASURE.name -> HOW_TO_GET_MORE_PLEASURE
            WAYS_TO_DEAL_WITH_CRAMPS.name -> WAYS_TO_DEAL_WITH_CRAMPS
            ALL_THINGS_MOOD_SWINGS.name -> ALL_THINGS_MOOD_SWINGS
            BEAT_THE_BLOAT.name -> BEAT_THE_BLOAT
            TIPS_TO_RELIEVE_PMS_SYMPTOM.name -> TIPS_TO_RELIEVE_PMS_SYMPTOM
            WORKOUTS_AND_YOUR_CYCLE.name -> WORKOUTS_AND_YOUR_CYCLE
            FEMALE_HEALTH_AND_NUTRITION.name -> FEMALE_HEALTH_AND_NUTRITION
            else -> null
        }
    }
}