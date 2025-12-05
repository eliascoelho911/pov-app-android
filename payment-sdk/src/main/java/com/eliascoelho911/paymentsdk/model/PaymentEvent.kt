package com.eliascoelho911.paymentsdk.model

sealed class PaymentEvent {
    data object WaitingForCard: PaymentEvent()
    data object WaitingForPin: PaymentEvent()
    data class CardRead(val card: CardDisplayInfo) : PaymentEvent()
    data object PinCollected : PaymentEvent()
    data object Processing: PaymentEvent()
    data class Finished(val status: PaymentStatus): PaymentEvent()
}
