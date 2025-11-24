package com.example.playlist_maker_android_sabitovratmir.data.network

import com.example.playlist_maker_android_sabitovratmir.domain.SearchHistoryRepository
import com.example.playlist_maker_android_sabitovratmir.domain.Word
import kotlinx.coroutines.CoroutineScope

class SearchHistoryRepositoryImpl(private val scope: CoroutineScope) : SearchHistoryRepository {
    private val database = DatabaseMock(scope = scope)

    override fun addToHistory(word: Word) {
        database.addToHistory(word = word)
    }

    override suspend fun getHistory(): List<Word> { // Теперь возвращает List<Word>
        return database.getHistory()
    }
}