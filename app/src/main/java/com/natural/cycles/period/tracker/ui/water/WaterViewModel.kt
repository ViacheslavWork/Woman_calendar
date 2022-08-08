package com.natural.cycles.period.tracker.ui.water

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.domain.usecase.days.GetDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.water.AddWaterUseCase
import com.natural.cycles.period.tracker.domain.usecase.water.GetWaterPerDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.water.SubWaterUseCase
import com.natural.cycles.period.tracker.utils.Constants

class WaterViewModel(
    private val getWaterPerDayUseCase: GetWaterPerDayUseCase,
    private val addWaterUseCase: AddWaterUseCase,
    private val subWaterUseCase: SubWaterUseCase,
    private val getDayUseCase: GetDayUseCase
) : ViewModel() {
    private val _volumeOfWater = MutableLiveData<Float>(0F)
    val volumeOfWater: LiveData<Float> = _volumeOfWater

    init {
        viewModelScope.launch { _volumeOfWater.postValue(getDayUseCase.execute(LocalDate.now()).volumeOfWater) }
    }

    fun getWaterPerDay(weight: Int?): Float {
        return getWaterPerDayUseCase.execute(weight)
    }

    fun addWater() {
        _volumeOfWater.postValue(volumeOfWater.value?.plus(Constants.UNIT_OF_WATER))
        viewModelScope.launch { addWaterUseCase.execute(LocalDate.now(), Constants.UNIT_OF_WATER) }
    }

    fun subWater() {
        val volumeOfWater = volumeOfWater.value?.minus(Constants.UNIT_OF_WATER)
        volumeOfWater?.let {
            if (it >= 0) {
                _volumeOfWater.postValue(it)
                viewModelScope.launch {
                    subWaterUseCase.execute(LocalDate.now(), Constants.UNIT_OF_WATER)
                }
            }
        }
    }
}