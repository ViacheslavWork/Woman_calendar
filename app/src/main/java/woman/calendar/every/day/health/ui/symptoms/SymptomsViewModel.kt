package woman.calendar.every.day.health.ui.symptoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.model.Symptom
import woman.calendar.every.day.health.domain.usecase.cycles.GetLastCyclesUseCase
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase
import woman.calendar.every.day.health.domain.usecase.symptoms.GetSymptomsUseCase
import woman.calendar.every.day.health.domain.usecase.symptoms.SaveSelectedSymptomsUseCase
import woman.calendar.every.day.health.ui.symptoms.SymptomEvent.OnSymptomClick

class SymptomsViewModel(
    private val getSymptomsUseCase: GetSymptomsUseCase,
    private val saveSelectedSymptomsUseCase: SaveSelectedSymptomsUseCase,
    private val getDayUseCase: GetDayUseCase,
    private val getLastCyclesUseCase: GetLastCyclesUseCase
) : ViewModel() {
    private val _symptoms = MutableLiveData<List<SymptomItem>>()
    val symptoms: LiveData<List<SymptomItem>> = _symptoms

    private val _volumeOfWater = MutableLiveData<Float>()
    val volumeOfWater: LiveData<Float> = _volumeOfWater

    private val _notes = MutableLiveData<String?>()
    val notes: LiveData<String?> = _notes

    private val selectedSymptoms = mutableSetOf<Symptom>()
    lateinit var date: LocalDate

    //TODO
    private val cycle = viewModelScope.async {
        getLastCyclesUseCase.execute(1).let {
            if (it.isNotEmpty()) return@let it[0]
            else return@let null
        }
    }

    fun init(date: LocalDate) {
        this.date = date
        downloadSymptoms(date)
        downloadNotes(date)
    }

    private fun downloadNotes(date: LocalDate) {
        viewModelScope.launch {
            _notes.postValue(getDayUseCase.execute(date).notes)
        }
    }

    fun updateUI() {
        setVolumeOfWater()
        downloadNotes(date)
    }

    private fun downloadSymptoms(date: LocalDate) {
        viewModelScope.launch {
            selectedSymptoms.addAll(getDayUseCase.execute(date).symptoms)
            _symptoms.value = getSymptomsUseCase.execute().map {
                SymptomItem.fromSymptom(it).apply { isChecked = selectedSymptoms.contains(it) }
            }
        }
    }

    fun saveSymptoms() {
        GlobalScope.launch {
            Timber.d("saveSymptoms")
            saveSelectedSymptomsUseCase.execute(
                date,
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

    private fun setVolumeOfWater() {
        viewModelScope.launch { _volumeOfWater.postValue(getDayUseCase.execute(date).volumeOfWater) }
    }

    suspend fun getDayOfLastCycle(): Int {
        return cycle.await()?.getDaysAfterStartOfPeriod() ?: 0
    }

    suspend fun isPeriod(date: LocalDate): Boolean {
        return cycle.await()?.period?.finish?.let { date.isAfter(it) } == true
    }
}