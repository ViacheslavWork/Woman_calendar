package com.period.tracker.natural.cycles.data.providers

import com.period.tracker.natural.cycles.R
import com.period.tracker.natural.cycles.domain.SymptomsProvider
import com.period.tracker.natural.cycles.domain.model.Symptom
import com.period.tracker.natural.cycles.domain.model.SymptomType.*

class SymptomsProviderImpl : SymptomsProvider {
    override fun getSymptoms(): List<Symptom> {
        return listOf(
            Symptom("Light", R.drawable.ic_light_men_flow, MENSTRUAL_FLOW),
            Symptom("Medium", R.drawable.ic_medium_men_flow, MENSTRUAL_FLOW),
            Symptom("Heavy", R.drawable.ic_heavy_men_flow, MENSTRUAL_FLOW),

            Symptom("Calm", R.drawable.ic_calm, MOOD),
            Symptom("Happy", R.drawable.ic_happy, MOOD),
            Symptom("Energetic", R.drawable.ic_energic, MOOD),
            Symptom("Frisky", R.drawable.ic_frisky, MOOD),
            Symptom("Mood swings", R.drawable.ic_mood_swing, MOOD),
            Symptom("Irritated", R.drawable.ic_irritated, MOOD),
            Symptom("Sad", R.drawable.ic_sad, MOOD),
            Symptom("Depressed", R.drawable.ic_depressed, MOOD),
            Symptom("Felling guilty", R.drawable.ic_felling_guilty, MOOD),
            Symptom("Obsessive thoughts", R.drawable.ic_obsessive_thoughts, MOOD),
            Symptom("Apathetic", R.drawable.ic_apathetic, MOOD),
            Symptom("Confused", R.drawable.ic_confused, MOOD),
            Symptom("Very self-critical", R.drawable.ic_very_self_critical, MOOD),

            Symptom("Don't have sex", R.drawable.ic_dont_have_sex, SEX_AND_SEX_DRIVE),
            Symptom("Protected sex", R.drawable.ic_protected_sex, SEX_AND_SEX_DRIVE),
            Symptom("Unprotected sex", R.drawable.ic_unprotected_sex, SEX_AND_SEX_DRIVE),
            Symptom("High sex drive", R.drawable.ic_high_sex_drive, SEX_AND_SEX_DRIVE),
            Symptom("Masturbation", R.drawable.ic_masturbation, SEX_AND_SEX_DRIVE),

            Symptom("Everything is fine", R.drawable.ic_everithing_fine, SYMPTOMS),
            Symptom("Cramps", R.drawable.ic_cramps, SYMPTOMS),
            Symptom("Tender breast", R.drawable.ic_tender_breast, SYMPTOMS),
            Symptom("Headache", R.drawable.ic_headache, SYMPTOMS),
            Symptom("Acne", R.drawable.ic_acne, SYMPTOMS),
            Symptom("Backache", R.drawable.ic_backache, SYMPTOMS),
            Symptom("Nausea", R.drawable.ic_nausea, SYMPTOMS),
            Symptom("Fatigue", R.drawable.ic_fatigue, SYMPTOMS),
            Symptom("Bloating", R.drawable.ic_bloating, SYMPTOMS),
            Symptom("Cravings", R.drawable.ic_cravings, SYMPTOMS),
            Symptom("Insomnia", R.drawable.ic_insomnia, SYMPTOMS),
            Symptom("Constipation", R.drawable.ic_constipation, SYMPTOMS),
            Symptom("Diarrhea", R.drawable.ic_diarhea, SYMPTOMS),

            Symptom("No discharge", R.drawable.ic_no_discharge, VAGINAL_DISCHARGE),
            Symptom("Spotting", R.drawable.ic_spotting, VAGINAL_DISCHARGE),
            Symptom("Sticky", R.drawable.ic_sticky, VAGINAL_DISCHARGE),
            Symptom("Creamy", R.drawable.ic_creamy, VAGINAL_DISCHARGE),
            Symptom("Eggwhite", R.drawable.ic_eggwhite, VAGINAL_DISCHARGE),
            Symptom("Watery", R.drawable.ic_watery, VAGINAL_DISCHARGE),
            Symptom("Unusual", R.drawable.ic_unusual, VAGINAL_DISCHARGE),

            Symptom("Travel", R.drawable.ic_travel, OTHER),
            Symptom("Stress", R.drawable.ic_stress, OTHER),
            Symptom("Disease or injury", R.drawable.ic_disease_of_injury, OTHER),
            Symptom("Alcohol", R.drawable.ic_alcohol, OTHER),
        )
    }
}