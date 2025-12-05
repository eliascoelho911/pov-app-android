package com.eliascoelho911.paymentsdk.gateway

import com.eliascoelho911.paymentsdk.external.client.PaymentClient
import com.eliascoelho911.paymentsdk.model.CardPayload
import com.eliascoelho911.paymentsdk.model.PaymentRequest
import com.eliascoelho911.paymentsdk.model.PaymentStatus

class PaymentGateway(
    private val paymentClient: PaymentClient
) {
    suspend fun processPayment(
        request: PaymentRequest,
        payload: CardPayload,
        cardPin: String?
    ): PaymentStatus {
        return paymentClient.processPayment(
            paymentRequest = request,
            cardPayload = payload,
            cardPin = cardPin
        )
    }
}
