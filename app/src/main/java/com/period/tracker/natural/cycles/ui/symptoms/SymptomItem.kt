package com.period.tracker.natural.cycles.ui.symptoms

import androidx.annotation.DrawableRes
import com.period.tracker.natural.cycles.domain.model.Symptom
import com.period.tracker.natural.cycles.domain.model.SymptomType

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