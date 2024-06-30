package com.simulation.newsly.model

data class Row(
    val _id: String,
    val anonymous: Boolean,
    val attributes: Attributes,
    val caption: String,
    val cards: List<Card>,
    val headerText: String,
    val maxClickCount: Int,
    val minimumTagCount: Int,
    val name: String,
    val score: Int,
    val type: String,
    val url: String,
    val width: Double
)