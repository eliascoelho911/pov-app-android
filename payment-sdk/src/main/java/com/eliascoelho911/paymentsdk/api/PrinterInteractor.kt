package com.eliascoelho911.paymentsdk.api

import com.eliascoelho911.paymentsdk.external.hardware.PrinterWriter

class PrinterInteractor internal constructor(
    private val printerWriter: PrinterWriter
) {
    suspend fun print(text: String) {
        printerWriter.print(text)
    }

    suspend fun println(text: String) {
        printerWriter.println(text)
    }

    suspend fun printLine() {
        printerWriter.printLine()
    }

    companion object {
        fun console() = PrinterInteractor(
            printerWriter = PrinterWriter.console()
        )
    }
}