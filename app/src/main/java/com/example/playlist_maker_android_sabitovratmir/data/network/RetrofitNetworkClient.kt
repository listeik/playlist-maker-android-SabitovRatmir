package com.example.playlist_maker_android_sabitovratmir.data.network

import com.example.playlist_maker_android_sabitovratmir.creator.Storage
import com.example.playlist_maker_android_sabitovratmir.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_sabitovratmir.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_sabitovratmir.domain.NetworkClient

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(request: Any): TracksSearchResponse {
        val searchList = storage.search((request as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}