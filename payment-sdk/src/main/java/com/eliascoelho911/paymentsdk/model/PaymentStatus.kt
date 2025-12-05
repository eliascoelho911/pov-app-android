package com.eliascoelho911.paymentsdk.model

sealed class PaymentStatus {
    data class Approved(
        val id: String,
        val maskedCard: String?,
        val brand: String?,
        val createdAt: Long
    ) : PaymentStatus()

    data class Declined(
        val reason: DeclineReason,
        val message: String? = null
    ) : PaymentStatus()

    data class Error(
        val message: String? = null,
        val cause: Throwable? = null
    ) : PaymentStatus()
}

enum class DeclineReason {
    INSUFFICIENT_FUNDS,
    CARD_EXPIRED,
    INVALID_CARD,
    SUSPECTED_FRAUD,
    OTHER
}
