package com.eliascoelho911.paymentsdk.gateway.stripe.mapper

import com.eliascoelho911.paymentsdk.api.PaymentStatus
import com.eliascoelho911.paymentsdk.gateway.stripe.StripePaymentResult

internal fun StripePaymentResult.getPaymentStatus(): PaymentStatus = when (status) {
    StripePaymentResult.Status.CANCELED -> PaymentStatus.Error(
        message = "The payment was canceled."
    )

    StripePaymentResult.Status.SUCCEEDED -> PaymentStatus.Approved(
        id = id,
        maskedCard = null, // TODO: implement masked card retrieval
        brand = null,    // TODO: implement brand retrieval
        createdAt = created
    )

    else -> PaymentStatus.Error(
        message = "Unhandled payment status: $this"
    )
}