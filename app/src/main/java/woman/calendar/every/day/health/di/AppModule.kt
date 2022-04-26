package woman.calendar.every.day.health.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import woman.calendar.every.day.health.ui.advices.AdvicesViewModel
import woman.calendar.every.day.health.ui.calendar.CalendarViewModel
import woman.calendar.every.day.health.ui.home.HomeViewModel


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
            getLastCyclesUseCase = get()
        )
    }
    viewModel { AdvicesViewModel() }
}