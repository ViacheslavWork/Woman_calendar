package com.period.tracker.natural.cycles.ui.subscription


class SubscriptionEvent(private val subscription: Subscription) {
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): Subscription? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            subscription
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekSubscription(): Subscription = subscription
}
