package me.brisson.tvcompose.domain.model

data class Movie(
    var id: Long,
    var title: String,
    var description: String,
    var backgroundImageUrl: String,
    var cardImageUrl: String,
    var videoUrl: String,
    var studio: String,
)
