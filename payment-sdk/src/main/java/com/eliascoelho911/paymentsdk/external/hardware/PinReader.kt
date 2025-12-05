package com.eliascoelho911.paymentsdk.external.hardware

internal interface PinReader {
    suspend fun waitAndReadPin(): String
}

internal class ConsolePinReader() : PinReader {
    override suspend fun waitAndReadPin(): String {
        return readln()
    }
}