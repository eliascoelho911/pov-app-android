package com.eliascoelho911.paymentsdk.gateway.sandbox

import com.eliascoelho911.paymentsdk.api.PaymentRequest
import com.eliascoelho911.paymentsdk.api.PaymentStatus
import com.eliascoelho911.paymentsdk.domain.model.CardPayload
import com.eliascoelho911.paymentsdk.gateway.PaymentGateway

internal class SandboxPaymentGateway : PaymentGateway {
    override suspend fun processPayment(
        request: PaymentRequest,
        payload: CardPayload
    ): PaymentStatus {

    }
}