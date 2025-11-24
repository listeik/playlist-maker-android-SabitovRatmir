package com.example.playlist_maker_android_sabitovratmir.data.network

import com.example.playlist_maker_android_sabitovratmir.data.network.DatabaseMock
import com.example.playlist_maker_android_sabitovratmir.domain.Playlist
import com.example.playlist_maker_android_sabitovratmir.domain.PlaylistsRepository
import com.example.playlist_maker_android_sabitovratmir.domain.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

//class PlaylistsRepositoryImpl(
//    private val scope: CoroutineScope
//) : PlaylistsRepository {
//    private val database = DatabaseMock(
//        scope = scope,
//    )
//
//    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
//        return database.getPlaylist(playlistId)
//    }
//
//    override fun getAllPlaylists(): Flow<Playlist?> {
//        return database.getAllPlaylists()
//    }
//
//    override suspend fun addNewPlaylist(id: Long, name: String, description: String, tracks: List<Track>) {
//        database.insertPlaylist(
//            Playlist(
//                id = id,
//                name = name,
//                description = description,
//                tracks = emptyList()
//            )
//        )
//    }
//
//    override suspend fun deletePlaylistById(id: Long) {
//        database.deletePlaylistById(id = id)
//    }
//}