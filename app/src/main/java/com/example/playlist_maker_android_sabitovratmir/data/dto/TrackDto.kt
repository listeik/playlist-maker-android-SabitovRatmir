package com.example.playlist_maker_android_sabitovratmir.data.dto

data class TrackDto(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: Int,
    val image: String = "",
    var favorite: Boolean = false,
    var playlistId: Long = 0
)