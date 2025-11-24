package com.example.playlist_maker_android_sabitovratmir.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_sabitovratmir.R
import com.example.playlist_maker_android_sabitovratmir.domain.Word
import com.example.playlist_maker_android_sabitovratmir.ui.theme.SurfaceGray
import com.example.playlist_maker_android_sabitovratmir.ui.theme.TextBlack
import com.example.playlist_maker_android_sabitovratmir.ui.theme.TextGray
import com.example.playlist_maker_android_sabitovratmir.ui.view_model.SearchState
import com.example.playlist_maker_android_sabitovratmir.ui.view_model.SearchViewModel
import com.example.playlist_maker_android_sabitovratmir.ui.view_model.TrackListItem

@Composable
fun SearchScreen(
    onBackClick: () -> Unit = {},
    onTrackClick: (Long) -> Unit = {}
) {
    val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
    val screenState by viewModel.searchScreenState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()

    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(screenState) {
        when (screenState) {
            is SearchState.Success -> {
                focusManager.clearFocus()
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon_description),
                        tint = TextBlack
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = stringResource(R.string.search),
                    color = TextBlack,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Search field with history
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceGray)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },

                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_screen_text),
                            color = TextGray,
                            fontSize = 18.sp
                        )
                    },
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                if (text.isNotEmpty()) {
                                    viewModel.search(text)
                                }
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.search_screen_text),
                                tint = TextGray
                            )
                        }
                    },
                    trailingIcon = {
                        if (text.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    text = ""
                                    viewModel.clearSearch()
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(R.string.clear_icon_description),
                                    tint = TextGray
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = TextBlack,
                        unfocusedTextColor = TextBlack,
                        cursorColor = TextBlack,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                // History dropdown - точно как в макете
                if (isFocused && text.isEmpty() && searchHistory.isNotEmpty()) {
                    HistoryRequests(
                        historyList = searchHistory,
                        onClick = { word ->
                            text = word.word
                            viewModel.onHistoryItemClick(word.word)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Content area
        when (screenState) {
            is SearchState.Initial -> {
                if (text.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.search_hint),
                            color = TextGray,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Нажмите кнопку поиска",
                            color = TextGray,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            is SearchState.Searching -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = TextBlack
                    )
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).list
                if (tracks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.nothing_found),
                            color = TextGray,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(tracks) { track ->
                            TrackListItem(
                                track = track,
                                onClick = { onTrackClick(track.id) }
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                thickness = 1.dp,
                                color = SurfaceGray
                            )
                        }
                    }
                }
            }

            is SearchState.Fail -> {
                val error = (screenState as SearchState.Fail).error
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.search_error_title, error),
                        color = TextGray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryRequests(
    historyList: List<Word>,
    onClick: (Word) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Divider как в макете - тонкая линия сверху
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = TextGray.copy(alpha = 0.2f)
        )

        // Список истории - без карточки, на белом фоне
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
        ) {
            items(historyList) { historyItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(historyItem) }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Иконка поиска как в макете
                    Icon(
                        painter = painterResource(R.drawable.history),
                        contentDescription = "История поиска",
                        modifier = Modifier.size(18.dp),
                        tint = TextGray
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = historyItem.word,
                        color = TextBlack,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        onBackClick = {},
        onTrackClick = {}
    )
}