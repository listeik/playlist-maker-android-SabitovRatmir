package com.example.playlist_maker_android_sabitovratmir.domain

interface SearchHistoryRepository {
    fun addToHistory(word: Word)
    suspend fun getHistory(): List<Word> // Возвращаем List<Word>, а не List<String>
}