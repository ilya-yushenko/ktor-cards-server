package org.yushenko.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class CardRequest(
    val id: Int? = null,
    val cardTitle: String,
    val cardDescription: String,
    val cardDate: String,
    val isVerified: Boolean = false
)
