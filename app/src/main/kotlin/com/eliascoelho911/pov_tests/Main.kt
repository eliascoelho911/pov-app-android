package com.eliascoelho911.pov_tests

import com.eliascoelho911.paymentsdk.api.PaymentEvent
import com.eliascoelho911.paymentsdk.api.PaymentMethod
import com.eliascoelho911.paymentsdk.api.PaymentRequest
import com.eliascoelho911.paymentsdk.api.PaymentStatus
import com.eliascoelho911.paymentsdk.device.DeviceCard
import com.eliascoelho911.paymentsdk.device.FakeDeviceInteractor
import com.eliascoelho911.paymentsdk.domain.PaymentFacadeImpl
import com.eliascoelho911.paymentsdk.domain.model.CardDisplayInfo
import com.eliascoelho911.paymentsdk.domain.model.CardPayload
import com.eliascoelho911.paymentsdk.gateway.sandbox.SandboxPaymentGateway
import com.eliascoelho911.paymentsdk.gateway.stripe.StripeClient
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.optional
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    val cliConfig = parseArguments(args)

    val request = PaymentRequest(
        amountCents = cliConfig.amount,
        method = cliConfig.method,
        installments = cliConfig.installments,
        description = "CLI payment"
    )

    val deviceInteractor = FakeDeviceInteractor(
        card = DeviceCard(
            payload = CardPayload(
                cardNumber = "4242424242424242",
                cardHolderName = "CLI User",
                expirationDate = "12/34",
                cvv = "123"
            ),
            displayInfo = CardDisplayInfo(
                maskedPan = "**** **** **** 4242",
                brand = "VISA"
            )
        ),
        delay = 500L
    )

    val paymentClient = PaymentFacadeImpl(
        gateway = SandboxPaymentGateway(StripeClient()),
        deviceInteractor = deviceInteractor
    )

    println("Iniciando pagamento de ${request.amountCents} centavos via ${request.method} em ${request.installments}x\n")

    paymentClient.startPayment(request).collect { event ->
        when (event) {
            PaymentEvent.WaitingForCard -> println("Aguardando cartão...")
            PaymentEvent.Processing -> println("Processando pagamento...")
            is PaymentEvent.Finished -> printStatus(event.status)
        }
    }
}

private fun parseArguments(args: Array<String>): PaymentCliConfig {
    val parser = ArgParser("povtests")
    val amount by parser.argument(ArgType.String, "amount", "Valor em centavos").optional()
    val method by parser.argument(ArgType.String, "method", "Método de pagamento").optional()
    val installments by parser.argument(ArgType.String, "installments", "Número de parcelas").optional()

    parser.parse(args)

    return PaymentCliConfig(
        amount = amount?.toLongOrNull()?.takeIf { it > 0 } ?: DEFAULT_AMOUNT,
        method = method
            ?.uppercase()
            ?.let { runCatching { PaymentMethod.valueOf(it) }.getOrNull() }
            ?: PaymentMethod.CREDIT,
        installments = installments?.toIntOrNull()?.takeIf { it > 0 } ?: DEFAULT_INSTALLMENTS
    )
}

private data class PaymentCliConfig(
    val amount: Long,
    val method: PaymentMethod,
    val installments: Int
)

private const val DEFAULT_AMOUNT = 1000L
private const val DEFAULT_INSTALLMENTS = 1

private fun printStatus(status: PaymentStatus) {
    when (status) {
        is PaymentStatus.Approved -> println(
            buildString {
                appendLine("Pagamento aprovado!")
                appendLine("ID: ${status.id}")
                appendLine("Bandeira: ${status.brand ?: "desconhecida"}")
                appendLine("Cartão: ${status.maskedCard ?: "não informado"}")
                appendLine("Criado em: ${status.createdAt}")
            }
        )

        is PaymentStatus.Declined -> println(
            buildString {
                appendLine("Pagamento recusado: ${status.reason}")
                status.message?.let { appendLine("Motivo: $it") }
            }
        )

        is PaymentStatus.Error -> println(
            buildString {
                appendLine("Erro no pagamento")
                status.message?.let { appendLine(it) }
                status.cause?.let { appendLine(it.stackTraceToString()) }
            }
        )
    }
}
