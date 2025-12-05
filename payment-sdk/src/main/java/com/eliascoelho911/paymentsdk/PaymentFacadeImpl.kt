package com.eliascoelho911.paymentsdk

import com.eliascoelho911.paymentsdk.service.PaymentService

internal class PaymentFacadeImpl(
    private val paymentService: PaymentService,
) : PaymentFacade,
    PaymentService by paymentService