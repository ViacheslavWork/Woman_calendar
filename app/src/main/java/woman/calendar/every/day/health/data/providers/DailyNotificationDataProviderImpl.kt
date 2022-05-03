package woman.calendar.every.day.health.data.providers

import android.content.Context
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.domain.DailyNotificationDataProvider
import woman.calendar.every.day.health.domain.model.DailyNotificationData
import woman.calendar.every.day.health.domain.model.Message

class DailyNotificationDataProviderImpl(val context: Context) : DailyNotificationDataProvider {
    override fun getPeriodStartData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_period_start,
            graphImage = R.drawable.im_graph_period,
            hormonesAndBody = context.getString(R.string.hormones_and_body_period_start),
            firstWinMessage = Message(
                title = context.getString(R.string.zen_vibes),
                content = context.getString(R.string.zen_vibes_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.battery_recharging),
                content = context.getString(R.string.battery_recharging_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.tricky_skin),
                content = context.getString(R.string.tricky_skin_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.period_pain),
                content = context.getString(R.string.period_pain_content)
            ),
            notificationText = context.getString(R.string.notification_period_start)
        )
    }

    override fun getPeriodMidData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_period_mid,
            graphImage = R.drawable.im_graph_period,
            hormonesAndBody = context.getString(R.string.hormones_and_body_period_mid),
            firstWinMessage = Message(
                title = context.getString(R.string.breast_comfort),
                content = context.getString(R.string.breast_comfort_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.better_digestion),
                content = context.getString(R.string.better_digestion_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.disrupted_sleep),
                content = context.getString(R.string.disrupted_sleep_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.low_mood),
                content = context.getString(R.string.low_mood_content)
            ),
            notificationText = context.getString(R.string.notification_period_mid)
        )
    }

    override fun getPeriodEndData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_period_end,
            graphImage = R.drawable.im_graph_period,
            hormonesAndBody = context.getString(R.string.hormones_and_body_period_end),
            firstWinMessage = Message(
                title = context.getString(R.string.breast_comfort),
                content = context.getString(R.string.breast_comfort_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.better_digestion),
                content = context.getString(R.string.better_digestion_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.disrupted_sleep),
                content = context.getString(R.string.disrupted_sleep_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.low_mood),
                content = context.getString(R.string.low_mood_content)
            ),
            notificationText = context.getString(R.string.notification_period_end)
        )
    }

    override fun getPostPeriodData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_post_period,
            graphImage = R.drawable.im_graph_post_period,
            hormonesAndBody = context.getString(R.string.hormones_and_body_post_period),
            firstWinMessage = Message(
                title = context.getString(R.string.greater_stamina),
                content = context.getString(R.string.greater_stamina_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.increased_productivity),
                content = context.getString(R.string.increased_productivity_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.overdoing_things),
                content = context.getString(R.string.overdoing_things_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.sensitive_hearing),
                content = context.getString(R.string.sensitive_hearing_content)
            ),
            notificationText = context.getString(R.string.notification_post_period_1)
        )
    }

    override fun getFertileStartData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_fertile_start,
            graphImage = R.drawable.im_graph_fertile,
            hormonesAndBody = context.getString(R.string.hormones_and_body_fertile_start),
            firstWinMessage = Message(
                title = context.getString(R.string.less_swelling),
                content = context.getString(R.string.less_swelling_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.emotional_intelligence),
                content = context.getString(R.string.emotional_intelligence_content_fertile)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.major_munchies),
                content = context.getString(R.string.major_munchies_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.energy_loss),
                content = context.getString(R.string.energy_loss_content)
            ),
            notificationText = context.getString(R.string.notification_fertile)
        )
    }

    override fun getFertileMidData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_fertile_mid,
            graphImage = R.drawable.im_graph_fertile,
            hormonesAndBody = context.getString(R.string.hormones_and_body_fertile_mid),
            firstWinMessage = Message(
                title = context.getString(R.string.less_swelling),
                content = context.getString(R.string.less_swelling_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.emotional_intelligence),
                content = context.getString(R.string.emotional_intelligence_content_fertile)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.major_munchies),
                content = context.getString(R.string.major_munchies_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.energy_loss),
                content = context.getString(R.string.energy_loss_content)
            ),
            notificationText = context.getString(R.string.notification_fertile)
        )
    }

    override fun getOvulationDayData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_ovulation_day,
            graphImage = R.drawable.im_graph_ovulation,
            hormonesAndBody = context.getString(R.string.hormones_and_body_ovulation_day),
            firstWinMessage = Message(
                title = context.getString(R.string.less_swelling),
                content = context.getString(R.string.less_swelling_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.emotional_intelligence),
                content = context.getString(R.string.emotional_intelligence_content_fertile)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.major_munchies),
                content = context.getString(R.string.major_munchies_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.energy_loss),
                content = context.getString(R.string.energy_loss_content)
            ),
            notificationText = context.getString(R.string.notification_ovulation_day)
        )
    }

    override fun getFertileEndData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_fertile_end,
            graphImage = R.drawable.im_graph_fertile,
            hormonesAndBody = context.getString(R.string.hormones_and_body_fertile_end),
            firstWinMessage = Message(
                title = context.getString(R.string.less_swelling),
                content = context.getString(R.string.less_swelling_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.emotional_intelligence),
                content = context.getString(R.string.emotional_intelligence_content_fertile)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.major_munchies),
                content = context.getString(R.string.major_munchies_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.energy_loss),
                content = context.getString(R.string.energy_loss_content)
            ),
            notificationText = context.getString(R.string.notification_fertile)
        )
    }

    override fun getPostFertileStartData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_post_fertile_start,
            graphImage = R.drawable.im_graph_post_fertile,
            hormonesAndBody = context.getString(R.string.hormones_and_body_post_fertile_start),
            firstWinMessage = Message(
                title = context.getString(R.string.easy_orientation),
                content = context.getString(R.string.easy_orientation_content_post_fertile)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.smooth_moves),
                content = context.getString(R.string.smooth_moves_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.feeling_sweaty),
                content = context.getString(R.string.feeling_sweaty_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.oily_skin),
                content = context.getString(R.string.oily_skin_content)
            ),
            notificationText = context.getString(R.string.notification_post_fertile_start_mid)
        )
    }

    override fun getPostFertileMidData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_post_fertile_mid,
            graphImage = R.drawable.im_graph_post_fertile,
            hormonesAndBody = context.getString(R.string.hormones_and_body_post_fertile_mid),
            firstWinMessage = Message(
                title = context.getString(R.string.easy_orientation),
                content = context.getString(R.string.easy_orientation_content_post_fertile)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.smooth_moves),
                content = context.getString(R.string.smooth_moves_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.feeling_sweaty),
                content = context.getString(R.string.feeling_sweaty_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.oily_skin),
                content = context.getString(R.string.oily_skin_content)
            ),
            notificationText = context.getString(R.string.notification_post_fertile_start_mid)
        )
    }

    override fun getPostFertileEndData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_post_fertile_end,
            graphImage = R.drawable.im_graph_post_fertile,
            hormonesAndBody = context.getString(R.string.hormones_and_body_post_fertile_end),
            firstWinMessage = Message(
                title = context.getString(R.string.emotional_intelligence),
                content = context.getString(R.string.emotional_intelligence_content_post_fertile)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.extra_empathy),
                content = context.getString(R.string.extra_empathy_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.oily_skin),
                content = context.getString(R.string.oily_skin_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.low_mood),
                content = context.getString(R.string.low_mood_content_post_fertile)
            ),
            notificationText = context.getString(R.string.notification_post_fertile_end)
        )
    }

    override fun getPrePeriodStartData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_pre_period_start,
            graphImage = R.drawable.im_graph_pre_period,
            hormonesAndBody = context.getString(R.string.hormones_and_body_pre_period_start),
            firstWinMessage = Message(
                title = context.getString(R.string.easy_orientation),
                content = context.getString(R.string.easy_orientation_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.emotional_intelligence),
                content = context.getString(R.string.emotional_intelligence_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.water_retention),
                content = context.getString(R.string.water_retention_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.breast_discomfort),
                content = context.getString(R.string.breast_discomfort_content)
            ),
            notificationText = context.getString(R.string.notification_pre_period_start)
        )
    }

    override fun getPrePeriodMidData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_pre_period_mid,
            graphImage = R.drawable.im_graph_pre_period,
            hormonesAndBody = context.getString(R.string.hormones_and_body_pre_period_mid),
            firstWinMessage = Message(
                title = context.getString(R.string.easy_orientation),
                content = context.getString(R.string.easy_orientation_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.emotional_intelligence),
                content = context.getString(R.string.emotional_intelligence_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.water_retention),
                content = context.getString(R.string.water_retention_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.breast_discomfort),
                content = context.getString(R.string.breast_discomfort_content)
            ),
            notificationText = context.getString(R.string.notification_pre_period_mid)
        )
    }

    override fun getPrePeriodEndData(): DailyNotificationData {
        return DailyNotificationData(
            circleImage = R.drawable.im_circle_pre_period_end,
            graphImage = R.drawable.im_graph_pre_period_end,
            hormonesAndBody = context.getString(R.string.hormones_and_body_pre_period_end),
            firstWinMessage = Message(
                title = context.getString(R.string.speedy_scanning),
                content = context.getString(R.string.speedy_scanning_content)
            ),
            secondWinMessage = Message(
                title = context.getString(R.string.winning_words),
                content = context.getString(R.string.winning_words_content)
            ),
            firstChallengesMessage = Message(
                title = context.getString(R.string.pre_period_pain),
                content = context.getString(R.string.pre_period_pain_content)
            ),
            secondChallengesMessage = Message(
                title = context.getString(R.string.disrupted_sleep),
                content = context.getString(R.string.disrupted_sleep_content)
            ),
            notificationText = context.getString(R.string.notification_pre_period_end)
        )
    }
}