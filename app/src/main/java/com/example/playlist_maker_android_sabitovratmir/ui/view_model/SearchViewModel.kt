package com.example.playlist_maker_android_sabitovratmir.ui.view_model

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_sabitovratmir.R
import com.example.playlist_maker_android_sabitovratmir.creator.Storage
import com.example.playlist_maker_android_sabitovratmir.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_sabitovratmir.data.network.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android_sabitovratmir.data.network.TracksRepositoryImpl
import com.example.playlist_maker_android_sabitovratmir.domain.Track
import com.example.playlist_maker_android_sabitovratmir.domain.TracksRepository
import com.example.playlist_maker_android_sabitovratmir.domain.Word
import com.example.playlist_maker_android_sabitovratmir.ui.theme.TextBlack
import com.example.playlist_maker_android_sabitovratmir.ui.theme.TextGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {
    private val searchHistoryRepository = SearchHistoryRepositoryImpl(scope = viewModelScope)

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<Word>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    init {
        loadSearchHistory()
    }

    fun search(whatSearch: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }

                searchHistoryRepository.addToHistory(Word(word = whatSearch))
                loadSearchHistory()

                val list = tracksRepository.searchTracks(expression = whatSearch)
                _searchScreenState.update { SearchState.Success(list = list) }

            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail(e.message.toString()) }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }

    private fun loadSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val history = searchHistoryRepository.getHistory()
            _searchHistory.update { history }
        }
    }

    fun onHistoryItemClick(query: String) {
        search(query)
    }

    suspend fun getHistoryList() = searchHistoryRepository.getHistory()

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.getTracksRepository()) as T
                }
            }
    }
}

@Composable
fun TrackListItem(
    track: Track,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = "${R.string.track_image_description} ${track.trackName}",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = track.trackName,
                fontWeight = FontWeight.Bold,
                color = TextBlack
            )
            Text(
                text = track.artistName,
                color = TextGray
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = track.trackTime,
                color = TextGray
            )
        }
    }
}

sealed class SearchState {
    object Initial: SearchState()
    object Searching: SearchState()
    data class Success(val list: List<Track>): SearchState()
    data class Fail(val error: String): SearchState()
}

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(
            RetrofitNetworkClient(Storage())
        )
    }
}