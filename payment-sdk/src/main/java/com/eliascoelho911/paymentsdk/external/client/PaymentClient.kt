package com.eliascoelho911.paymentsdk.external.client

import com.eliascoelho911.paymentsdk.model.CardPayload
import com.eliascoelho911.paymentsdk.model.PaymentRequest
import com.eliascoelho911.paymentsdk.model.PaymentStatus

internal interface PaymentClient {
    suspend fun processPayment(
        paymentRequest: PaymentRequest,
        cardPayload: CardPayload,
        cardPin: String?
    ): PaymentStatus
}