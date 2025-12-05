package com.eliascoelho911.paymentsdk.service

import com.eliascoelho911.paymentsdk.external.hardware.CardReader
import com.eliascoelho911.paymentsdk.external.hardware.PinReader
import com.eliascoelho911.paymentsdk.external.hardware.PrinterWriter
import com.eliascoelho911.paymentsdk.gateway.PaymentGateway
import com.eliascoelho911.paymentsdk.model.PaymentEvent
import com.eliascoelho911.paymentsdk.model.PaymentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class SandboxPaymentService(
    private val gateway: PaymentGateway,
    private val cardReader: CardReader,
    private val pinReader: PinReader,
    private val printerWriter: PrinterWriter
): PaymentService {
    override suspend fun startPayment(request: PaymentRequest): Flow<PaymentEvent> =flow {
        emit(PaymentEvent.WaitingForCard)

        val card = cardReader.waitAndReadCard()
        emit(PaymentEvent.Processing)

        val paymentResult = gateway.processPayment(request, card.payload)

        emit(PaymentEvent.Finished(paymentResult))
    }.flowOn(Dispatchers.Default)
}