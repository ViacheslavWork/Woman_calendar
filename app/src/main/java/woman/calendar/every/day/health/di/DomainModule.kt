package woman.calendar.every.day.health.di

import org.koin.dsl.module
import woman.calendar.every.day.health.domain.usecase.GetDailyNotificationDataUseCase
import woman.calendar.every.day.health.domain.usecase.RecalculateFromDayUseCase
import woman.calendar.every.day.health.domain.usecase.articles.GetArticleGroupsUseCase
import woman.calendar.every.day.health.domain.usecase.articles.GetArticleUseCase
import woman.calendar.every.day.health.domain.usecase.articles.GetArticlesFlowUseCase
import woman.calendar.every.day.health.domain.usecase.articles.GetArticlesUseCase
import woman.calendar.every.day.health.domain.usecase.cycles.GetCycleUseCase
import woman.calendar.every.day.health.domain.usecase.cycles.GetLastCyclesUseCase
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase
import woman.calendar.every.day.health.domain.usecase.days.GetMonthUseCase
import woman.calendar.every.day.health.domain.usecase.days.GetWeekUseCase
import woman.calendar.every.day.health.domain.usecase.days.SaveDayUseCase
import woman.calendar.every.day.health.domain.usecase.notification.OnOffEverydayNotificationUseCase
import woman.calendar.every.day.health.domain.usecase.periods.*
import woman.calendar.every.day.health.domain.usecase.symptoms.GetSymptomsUseCase
import woman.calendar.every.day.health.domain.usecase.symptoms.SaveSelectedSymptomsUseCase
import woman.calendar.every.day.health.domain.usecase.water.AddWaterUseCase
import woman.calendar.every.day.health.domain.usecase.water.GetWaterPerDayUseCase
import woman.calendar.every.day.health.domain.usecase.water.SubWaterUseCase

val domainModule = module {

    single<GetMonthUseCase> { GetMonthUseCase(repository = get()) }
    single<GetDayUseCase> { GetDayUseCase(repository = get()) }
    single<SaveDayUseCase> { SaveDayUseCase(repository = get()) }
    single<GetWeekUseCase> { GetWeekUseCase(repository = get()) }
    single<GetLastPeriodsUseCase> { GetLastPeriodsUseCase(repository = get()) }
    single<GetLastCyclesUseCase> { GetLastCyclesUseCase(repository = get()) }
    single<GetCycleUseCase> { GetCycleUseCase(getDayUseCase = get()) }
    single<GetCountOfPeriodsUseCase> { GetCountOfPeriodsUseCase(repository = get()) }
    single<RecalculateFromDayUseCase> {
        RecalculateFromDayUseCase(
            repository = get(),
            getDayUseCase = get(),
            latestPeriodPreferences = get()
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
    single {
        MarkDayUseCase(
            repository = get(),
            getDayUseCase = get(),
            getCountOfPeriodsUseCase = get(),
            getEarliestPeriodUseCase = get(),
            earliestPeriodPreferences = get(),
            latestPeriodPreferences = get(),
            getLastPeriodsUseCase = get()
        )
    }
    single { GetEarliestPeriodUseCase(getDayUseCase = get()) }
    //notifications
    single {
        GetDailyNotificationDataUseCase(
            getNotificationDataProvider = get(),
            getLastCyclesUseCase = get()
        )
    }
    //articles
    single { GetArticleGroupsUseCase(articlesProvider = get()) }
    single { GetArticlesUseCase(articlesProvider = get()) }
    single { GetArticlesFlowUseCase(articlesProvider = get()) }
    single { GetArticleUseCase(articlesProvider = get()) }
    //water
    single<GetWaterPerDayUseCase> { GetWaterPerDayUseCase() }
    single<AddWaterUseCase> { AddWaterUseCase(getDayUseCase = get(), repository = get()) }
    single<SubWaterUseCase> { SubWaterUseCase(getDayUseCase = get(), repository = get()) }
    //notification
    single {
        OnOffEverydayNotificationUseCase(
            everydayNotificationScheduler = get(),
            getCountOfPeriodsUseCase = get(),
            notificationSchedulerPreferences = get()
        )
    }
}
