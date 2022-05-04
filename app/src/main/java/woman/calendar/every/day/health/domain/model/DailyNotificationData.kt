package woman.calendar.every.day.health.domain.model

import androidx.annotation.DrawableRes

data class DailyNotificationData(
    @DrawableRes val circleImage: Int,
    @DrawableRes val graphImage: Int,
    val hormonesAndBody: String,
    val firstWinMessage: Message,
    val secondWinMessage: Message,
    val firstChallengesMessage: Message,
    val secondChallengesMessage: Message,
    val notificationText: String
)
enum class DailyNotificationStatus {
    PERIOD_START,
    PERIOD_MID,
    PERIOD_END,
    POST_PERIOD,
    FERTILE_START,
    FERTILE_MID,
    FERTILE_END,
    OVULATION_DAY,
    POST_FERTILE_START,
    POST_FERTILE_MID,
    POST_FERTILE_END,
    PRE_PERIOD_START,
    PRE_PERIOD_MID,
    PRE_PERIOD_END,
}


