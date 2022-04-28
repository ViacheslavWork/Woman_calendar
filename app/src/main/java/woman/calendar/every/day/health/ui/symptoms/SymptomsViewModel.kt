package woman.calendar.every.day.health.ui.symptoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Symptom
import woman.calendar.every.day.health.domain.usecase.GetDayUseCase
import woman.calendar.every.day.health.domain.usecase.GetLastCyclesUseCase
import woman.calendar.every.day.health.domain.usecase.GetSymptomsUseCase
import woman.calendar.every.day.health.domain.usecase.SaveSelectedSymptomsUseCase
import woman.calendar.every.day.health.ui.symptoms.SymptomEvent.OnSymptomClick

class SymptomsViewModel(
    private val getSymptomsUseCase: GetSymptomsUseCase,
    private val saveSelectedSymptomsUseCase: SaveSelectedSymptomsUseCase,
    private val getDayUseCase: GetDayUseCase,
    private val getLastCyclesUseCase: GetLastCyclesUseCase
) : ViewModel() {
    private val _symptoms = MutableLiveData<List<SymptomItem>>()
    val symptoms: LiveData<List<SymptomItem>> = _symptoms

    private val selectedSymptoms = mutableSetOf<Symptom>()
    private val date = LocalDate.now()

    init {
        downloadSymptoms()
    }

    private fun downloadSymptoms() {
        viewModelScope.launch {
            selectedSymptoms.addAll(getDayUseCase.execute(date).symptoms)
            _symptoms.value = getSymptomsUseCase.execute().map {
                SymptomItem.fromSymptom(it).apply { isChecked = selectedSymptoms.contains(it) }
            }
        }
    }

    fun saveSymptoms() {
        viewModelScope.launch {
            saveSelectedSymptomsUseCase.execute(
                LocalDate.now(),
                selectedSymptoms
            )
        }
    }

    fun handleEvent(event: SymptomEvent?) {
        when (event) {
            is OnSymptomClick -> {
                viewModelScope.launch {
                    if (event.symptom.isChecked) {
                        selectedSymptoms.add(event.symptom.toSymptom())
                    } else {
                        selectedSymptoms.remove(event.symptom.toSymptom())
                    }
                }
            }
        }
    }

    suspend fun getDayOfLastCycle(): Int {
        return getLastCyclesUseCase.execute(1).let {
            if (it.isNotEmpty()) return@let it[0].getDaysAfterStartOfPeriod()
            else return@let 0
        }
    }
}