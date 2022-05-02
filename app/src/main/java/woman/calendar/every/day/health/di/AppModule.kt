package woman.calendar.every.day.health.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import woman.calendar.every.day.health.ui.articles.ArticlesViewModel
import woman.calendar.every.day.health.ui.articles.details.ArticleDetailsViewModel
import woman.calendar.every.day.health.ui.articles.discover.DiscoverViewModel
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
    viewModel { ArticlesViewModel() }
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
    viewModel { DiscoverViewModel(getArticleGroupsUseCase = get()) }
    viewModel { ArticleDetailsViewModel(getArticleUseCase = get()) }
}