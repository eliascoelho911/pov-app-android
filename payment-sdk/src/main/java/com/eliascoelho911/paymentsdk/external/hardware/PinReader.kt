package com.eliascoelho911.paymentsdk.external.hardware

import kotlinx.coroutines.delay

interface PinReader {
    suspend fun waitAndReadPin(): String
}

internal class FakePinReader(
    private val pin: String = defaultPin,
    val delay: Long = 500L,
) : PinReader {
    override suspend fun waitAndReadPin(): String {
        delay(delay)
        return pin
    }
}

internal const val defaultPin = "1234"