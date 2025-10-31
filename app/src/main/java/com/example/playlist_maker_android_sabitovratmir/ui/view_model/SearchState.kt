package com.example.playlist_maker_android_sabitovratmir.ui.view_model

import com.example.playlist_maker_android_sabitovratmir.creator.Storage
import com.example.playlist_maker_android_sabitovratmir.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_sabitovratmir.data.network.Track
import com.example.playlist_maker_android_sabitovratmir.data.network.TracksRepositoryImpl
import com.example.playlist_maker_android_sabitovratmir.domain.TracksRepository

sealed class SearchState {
    object Initial: SearchState() // Первоначальное cостояние экрана
    object Searching: SearchState() // Cостояние экрана при начале поиска
    data class Success(val list: List<Track>): SearchState() // Cостояние экрана при успешном завершении поиска
    data class Fail(val error: String): SearchState() // Cостояние экрана, если при запросе к серверу произошла ошибка
}

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}