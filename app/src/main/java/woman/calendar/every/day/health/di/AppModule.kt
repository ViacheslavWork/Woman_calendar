package woman.calendar.every.day.health.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import woman.calendar.every.day.health.ui.advices.AdvicesViewModel
import woman.calendar.every.day.health.ui.calendar.CalendarViewModel
import woman.calendar.every.day.health.ui.home.HomeViewModel
import woman.calendar.every.day.health.ui.symptoms.SymptomsViewModel
import woman.calendar.every.day.health.ui.water.WaterViewModel


val appModule = module {
    viewModel {
        CalendarViewModel(
            getMonthUseCase = get(),
            updatePeriodDayUseCase = get(),
        )
    }
    viewModel {
        HomeViewModel(
            getWeekUseCase = get(),
            getCountOfPeriodsUseCase = get(),
            getLastCyclesUseCase = get(),
            getDayUseCase = get()
        )
    }
    viewModel { AdvicesViewModel() }
    viewModel {
        WaterViewModel(
            getWaterPerDayUseCase = get(),
            addWaterUseCase = get(),
            subWaterUseCase = get(),
            getDayUseCase = get()
        )
    }
    viewModel {
        SymptomsViewModel(
            getSymptomsUseCase = get(),
            saveSelectedSymptomsUseCase = get(),
            getDayUseCase = get(),
            getLastCyclesUseCase = get(),
        )
    }
}