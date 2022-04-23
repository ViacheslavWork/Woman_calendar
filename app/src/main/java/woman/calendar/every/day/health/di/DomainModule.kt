package woman.calendar.every.day.health.di

import org.koin.dsl.module
import woman.calendar.every.day.health.domain.usecase.*

val domainModule = module {
    single<GetMonthUseCase> { GetMonthUseCase(repository = get()) }
    single<RecalculateFromDayUseCase> {
        RecalculateFromDayUseCase(
            repository = get(),
            recalculateAveragePeriodIntervalUseCase = get()
        )
    }
    single<RecalculateAveragePeriodIntervalUseCase> {
        RecalculateAveragePeriodIntervalUseCase(repository = get())
    }
    single<MarkPeriodDayUseCase> {
        MarkPeriodDayUseCase(
            repository = get(),
            recalculateFromDayUseCase = get()
        )
    }
    single<UnmarkPeriodDayUseCase> {
        UnmarkPeriodDayUseCase(
            repository = get(),
            recalculateFromDayUseCase = get()
        )
    }
}
