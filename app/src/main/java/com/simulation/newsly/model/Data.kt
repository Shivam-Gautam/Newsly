package com.simulation.newsly.model

data class Data(
    val classId: String,
    val displayName: String,
    val name: String,
    val nextToken: String,
    val rows: List<Row>
)