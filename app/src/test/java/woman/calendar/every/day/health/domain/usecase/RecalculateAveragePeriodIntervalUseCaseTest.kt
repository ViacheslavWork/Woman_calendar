package woman.calendar.every.day.health.domain.usecase

import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.data.RepositoryImpl
import woman.calendar.every.day.health.domain.model.Day

class RecalculateAveragePeriodIntervalUseCaseTest {
    @Test
    fun testAverageInterval() {
        val repository = RepositoryImpl()
        repository.setDay(Day(LocalDate.of(2022,4,20)))
        repository.setDay(Day(LocalDate.of(2022,3,20)))
        repository.setDay(Day(LocalDate.of(2022,2,20)))

        val actualAverageInterval = RecalculateAveragePeriodIntervalUseCase(repository).execute()

        assertEquals(30, actualAverageInterval)
    }
}