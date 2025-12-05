package com.eliascoelho911.paymentsdk.model

data class CardDisplayInfo(
    val maskedPan: String,
    val brand: String
)

data class CardPayload(
    val cardNumber: String,
    val cardHolderName: String,
    val expirationDate: String,
    val cvv: String
)

internal data class Card(
    val payload: CardPayload,
    val displayInfo: CardDisplayInfo
)