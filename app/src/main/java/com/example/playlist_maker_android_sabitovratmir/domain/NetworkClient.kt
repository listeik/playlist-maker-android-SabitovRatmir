package com.example.playlist_maker_android_sabitovratmir.domain

import com.example.playlist_maker_android_sabitovratmir.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}