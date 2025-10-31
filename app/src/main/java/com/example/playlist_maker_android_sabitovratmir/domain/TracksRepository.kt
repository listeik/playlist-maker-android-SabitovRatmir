package com.example.playlist_maker_android_sabitovratmir.domain

import com.example.playlist_maker_android_sabitovratmir.data.network.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}