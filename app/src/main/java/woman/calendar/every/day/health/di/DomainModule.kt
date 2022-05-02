package woman.calendar.every.day.health.di

import org.koin.dsl.module
import woman.calendar.every.day.health.domain.usecase.*
import woman.calendar.every.day.health.domain.usecase.articles.GetArticleGroupsUseCase
import woman.calendar.every.day.health.domain.usecase.articles.GetArticleUseCase
import woman.calendar.every.day.health.domain.usecase.water.AddWaterUseCase
import woman.calendar.every.day.health.domain.usecase.water.GetWaterPerDayUseCase
import woman.calendar.every.day.health.domain.usecase.water.SubWaterUseCase

val domainModule = module {
    single<GetMonthUseCase> { GetMonthUseCase(repository = get()) }
    single<GetDayUseCase> { GetDayUseCase(repository = get()) }
    single<GetWeekUseCase> { GetWeekUseCase(repository = get()) }
    single<GetLastPeriodsUseCase> { GetLastPeriodsUseCase(repository = get()) }
    single<GetLastCyclesUseCase> { GetLastCyclesUseCase(repository = get()) }
    single<GetCountOfPeriodsUseCase> { GetCountOfPeriodsUseCase(repository = get()) }
    single<RecalculateFromDayUseCase> {
        RecalculateFromDayUseCase(
            repository = get(),
            getDayUseCase = get()
        )
    }
    single<GetSymptomsUseCase> { GetSymptomsUseCase(symptomsProvider = get()) }
    single<SaveSelectedSymptomsUseCase> {
        SaveSelectedSymptomsUseCase(
            repository = get(),
            getDayUseCase = get()
        )
    }

    single<UpdatePeriodDayUseCase> {
        UpdatePeriodDayUseCase(
            repository = get(),
            recalculateFromDayUseCase = get()
        )
    }
    //articles
    single { GetArticleGroupsUseCase(articlesProvider = get()) }
    single { GetArticleUseCase(articlesProvider = get()) }
    //water
    single<GetWaterPerDayUseCase> { GetWaterPerDayUseCase() }
    single<AddWaterUseCase> { AddWaterUseCase(getDayUseCase = get(), repository = get()) }
    single<SubWaterUseCase> { SubWaterUseCase(getDayUseCase = get(), repository = get()) }
}
