package com.eliascoelho911.paymentsdk.device

internal interface DeviceInteractor {
    suspend fun waitAndReadCard(): DeviceCard
}