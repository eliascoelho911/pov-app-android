package com.eliascoelho911.paymentsdk.gateway.stripe

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

@OptIn(ExperimentalSerializationApi::class)
internal val stripeJson = Json {
    namingStrategy = JsonNamingStrategy.SnakeCase
    decodeEnumsCaseInsensitive = true
    ignoreUnknownKeys = true
}
