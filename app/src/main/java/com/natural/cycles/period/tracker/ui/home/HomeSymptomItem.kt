package com.natural.cycles.period.tracker.ui.home

import androidx.annotation.DrawableRes
import com.natural.cycles.period.tracker.domain.model.Symptom
import com.natural.cycles.period.tracker.domain.model.SymptomType

data class HomeSymptomItem(
    val title: String,
    @DrawableRes
    val image: Int,
    val symptomType: SymptomType
) {
    companion object {
        fun fromSymptom(symptom: Symptom): HomeSymptomItem {
            return HomeSymptomItem(
                title = symptom.title,
                image = symptom.image,
                symptomType = symptom.symptomType,
            )
        }
    }
}

fun HomeSymptomItem.toSymptom(): Symptom {
    return Symptom(
        title = title,
        image = image,
        symptomType = symptomType,
    )
}
