package com.natural.cycles.period.tracker.utils

import timber.log.Timber

object IdHelper {
    private var number = 0
    fun getId(): Int {
        number += 1
        Timber.d(number.toString())
        return number
//        return UUID.randomUUID().toString()
    }
}