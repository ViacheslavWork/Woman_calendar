package woman.calendar.every.day.health.di

import org.koin.dsl.module
import woman.calendar.every.day.health.domain.usecase.*

val domainModule = module {
    single<GetMonthUseCase> { GetMonthUseCase(repository = get()) }
    single<GetDayUseCase> { GetDayUseCase(repository = get()) }
    single<GetWeekUseCase> { GetWeekUseCase(repository = get()) }
    single<GetLastPeriodsUseCase> { GetLastPeriodsUseCase(repository = get()) }
    single<GetLastCyclesUseCase> { GetLastCyclesUseCase(repository = get()) }
    single<GetCountOfPeriodsUseCase> { GetCountOfPeriodsUseCase(repository = get()) }
    single<RecalculateFromDayUseCase> { RecalculateFromDayUseCase(repository = get()) }
    single<GetSymptomsUseCase> { GetSymptomsUseCase(symptomsProvider = get()) }
    single<SaveSelectedSymptomsUseCase> { SaveSelectedSymptomsUseCase(repository = get()) }

    single<UpdatePeriodDayUseCase> {
        UpdatePeriodDayUseCase(
            repository = get(),
            recalculateFromDayUseCase = get()
        )
    }
}
