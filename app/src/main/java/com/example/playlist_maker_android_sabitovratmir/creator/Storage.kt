package com.example.playlist_maker_android_sabitovratmir.creator

import com.example.playlist_maker_android_sabitovratmir.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            id = 1,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTime = 158000
        ),
        TrackDto(
            id = 2,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTime = 283000
        ),
        TrackDto(
            id = 3,
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTime = 312000
        ),
        TrackDto(
            id = 4,
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTime = 225000
        ),
        TrackDto(
            id = 5,
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTime = 272000
        ),
        TrackDto(
            id = 6,
            trackName = "На заре",
            artistName = "Альянс",
            trackTime = 230000
        ),
        TrackDto(
            id = 7,
            trackName = "Перемен",
            artistName = "Кино",
            trackTime = 296000
        ),
        TrackDto(
            id = 8,
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTime = 195000
        ),
        TrackDto(
            id = 9,
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTime = 222000
        ),
        TrackDto(
            id = 10,
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTime = 241000
        )
    )

    fun getTrackById(id: Long): TrackDto? {
        return listTracks.firstOrNull { it.id == id }
    }

    fun search(request: String): List<TrackDto> {
        val result = listTracks.filter {
            it.trackName.lowercase().contains(request.lowercase()) || it.artistName.lowercase().contains(request.lowercase())
        }
        return result
    }
}