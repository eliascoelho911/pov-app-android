package com.eliascoelho911.paymentsdk.api

import kotlinx.coroutines.flow.Flow

interface PaymentClient {
    suspend fun startPayment(request: PaymentRequest): Flow<PaymentEvent>
}

sealed class PaymentEvent {
    data object WaitingForCard: PaymentEvent()
    data object Processing: PaymentEvent()
    data class Finished(val status: PaymentStatus): PaymentEvent()
}