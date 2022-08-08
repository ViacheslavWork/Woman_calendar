package com.natural.cycles.period.tracker.ui.symptoms

import androidx.annotation.DrawableRes
import com.natural.cycles.period.tracker.domain.model.Symptom
import com.natural.cycles.period.tracker.domain.model.SymptomType

data class SymptomItem(
    val title: String,
    @DrawableRes val image: Int,
    val symptomType: SymptomType,
    var isChecked: Boolean = false
) {
    companion object {
        fun fromSymptom(symptom: Symptom): SymptomItem {
            return SymptomItem(
                title = symptom.title,
                image = symptom.image,
                symptomType = symptom.symptomType,
            )
        }
    }
}

fun SymptomItem.toSymptom(): Symptom {
    return Symptom(
        title = title,
        image = image,
        symptomType = symptomType,
    )
}