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


