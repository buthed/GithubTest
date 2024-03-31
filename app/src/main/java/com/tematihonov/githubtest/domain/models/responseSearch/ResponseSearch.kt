package com.tematihonov.githubtest.domain.models.responseSearch

data class ResponseSearch(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int,
)