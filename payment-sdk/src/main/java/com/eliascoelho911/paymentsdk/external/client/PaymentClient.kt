package com.eliascoelho911.paymentsdk.external.client

import com.eliascoelho911.paymentsdk.model.CardPayload
import com.eliascoelho911.paymentsdk.model.PaymentRequest
import com.eliascoelho911.paymentsdk.model.PaymentStatus

interface PaymentClient {
    suspend fun processPayment(
        paymentRequest: PaymentRequest,
        cardPayload: CardPayload
    ): PaymentStatus
}