package com.natural.cycles.period.tracker.domain.model

import androidx.annotation.DrawableRes
import com.natural.cycles.period.tracker.R

data class Symptom(
    val title: String,
    @DrawableRes val image: Int,
    val symptomType: SymptomType
)

enum class SymptomType(@DrawableRes val background: Int) {
    MENSTRUAL_FLOW(R.drawable.bg_circle_red_pale),
    SEX_AND_SEX_DRIVE(R.drawable.bg_circle_pink_dark_1),
    MOOD(R.drawable.bg_circle_yellow),
    SYMPTOMS(R.drawable.bg_circle_pink_dark_2),
    VAGINAL_DISCHARGE(R.drawable.bg_circle_lilac_pale),
    OTHER(R.drawable.bg_circle_lilac)
}