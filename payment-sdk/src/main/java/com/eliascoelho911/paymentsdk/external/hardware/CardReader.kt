package com.eliascoelho911.paymentsdk.external.hardware

import com.eliascoelho911.paymentsdk.model.Card
import com.eliascoelho911.paymentsdk.model.CardDisplayInfo
import com.eliascoelho911.paymentsdk.model.CardPayload
import kotlinx.coroutines.delay

internal interface CardReader {
    suspend fun waitAndReadCard(): Card
}

internal class FakeCardReader(
    private val card: Card = defaultCard,
    val delay: Long = 2000L,
): CardReader {
    override suspend fun waitAndReadCard(): Card {
        delay(delay)
        return card
    }
}

internal val defaultCard = Card(
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
)