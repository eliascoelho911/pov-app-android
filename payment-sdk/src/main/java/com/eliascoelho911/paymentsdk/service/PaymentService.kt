package com.eliascoelho911.paymentsdk.service

import com.eliascoelho911.paymentsdk.model.PaymentEvent
import com.eliascoelho911.paymentsdk.model.PaymentRequest
import kotlinx.coroutines.flow.Flow

interface PaymentService {
    suspend fun startPayment(request: PaymentRequest): Flow<PaymentEvent>
}