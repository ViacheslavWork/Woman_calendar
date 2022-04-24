package woman.calendar.every.day.health.di

import org.koin.dsl.module
import woman.calendar.every.day.health.domain.usecase.GetMonthUseCase
import woman.calendar.every.day.health.domain.usecase.UpdatePeriodDayUseCase
import woman.calendar.every.day.health.domain.usecase.RecalculateFromDayUseCase

val domainModule = module {
    single<GetMonthUseCase> { GetMonthUseCase(repository = get()) }
    single<RecalculateFromDayUseCase> {
        RecalculateFromDayUseCase(
            repository = get(),
        )
    }

    single<UpdatePeriodDayUseCase> {
        UpdatePeriodDayUseCase(
            repository = get(),
            recalculateFromDayUseCase = get()
        )
    }
}
