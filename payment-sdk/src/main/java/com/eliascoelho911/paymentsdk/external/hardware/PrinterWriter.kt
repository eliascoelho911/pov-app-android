package com.eliascoelho911.paymentsdk.external.hardware

import kotlinx.coroutines.delay

interface PrinterWriter {
    suspend fun print(text: String)
}

internal class FakePrinterWriter(
    val delay: Long = 250L,
) : PrinterWriter {
    override suspend fun print(text: String) {
        delay(delay)
        println(text)
    }
}
