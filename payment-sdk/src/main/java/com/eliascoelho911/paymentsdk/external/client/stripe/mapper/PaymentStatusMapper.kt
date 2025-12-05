package com.eliascoelho911.paymentsdk.external.client.stripe.mapper

import com.eliascoelho911.paymentsdk.external.client.stripe.StripePaymentResult
import com.eliascoelho911.paymentsdk.model.PaymentStatus

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