package com.example.playlist_maker_android_sabitovratmir.domain
data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val tracks: List<Track>
)