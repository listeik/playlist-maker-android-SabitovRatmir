package com.example.playlist_maker_android_sabitovratmir.domain

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}