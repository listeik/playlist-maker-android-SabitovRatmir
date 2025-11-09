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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.playlist_maker_android_sabitovratmir.R
import com.example.playlist_maker_android_sabitovratmir.domain.Track
import com.example.playlist_maker_android_sabitovratmir.ui.theme.SurfaceGray
import com.example.playlist_maker_android_sabitovratmir.ui.theme.TextBlack
import com.example.playlist_maker_android_sabitovratmir.ui.theme.TextGray
import com.example.playlist_maker_android_sabitovratmir.ui.view_model.SearchState
import com.example.playlist_maker_android_sabitovratmir.ui.view_model.SearchViewModel
import com.example.playlist_maker_android_sabitovratmir.ui.view_model.TrackListItem

@Composable
fun SearchScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }

    val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
    val searchState by viewModel.searchScreenState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back_icon_description),
                    tint = TextBlack
                )
            }

            Text(
                text = stringResource(R.string.search),
                color = TextBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 66.dp)
            )
        }

        SearchTextField(
            searchText = searchText,
            onSearchTextChange = {
                searchText = it
            },
            onSearchClick = {
                if (searchText.isNotEmpty()) {
                    viewModel.search(searchText)
                }
            },
            onClearClick = {
                searchText = ""
                viewModel.clearSearch()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (searchState) {
            is SearchState.Initial -> {
                if (searchText.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.search_hint),
                            color = TextGray,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            is SearchState.Searching -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = TextBlack
                    )
                }
            }

            is SearchState.Success -> {
                val tracks = (searchState as SearchState.Success).list
                if (tracks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
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
                            .padding(horizontal = 16.dp)
                    ) {
                        items(tracks.size) { index ->
                            TrackListItem(track = tracks[index])
                        }
                    }
                }
            }

            is SearchState.Fail -> {
                val error = (searchState as SearchState.Fail).error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.search_error_title),
                            color = TextBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error,
                            color = TextGray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun SearchTextField(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.search_screen_text),
                color = TextGray,
                fontSize = 18.sp
            )
        },
        leadingIcon = {
            IconButton(
                onClick = onSearchClick,
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
            if (searchText.isNotEmpty()) {
                IconButton(
                    onClick = onClearClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear_icon_description),
                        modifier = Modifier.size(24.dp),
                        tint = TextGray
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SurfaceGray,
            unfocusedBorderColor = SurfaceGray,
            focusedTextColor = TextBlack,
            unfocusedTextColor = TextBlack,
            cursorColor = TextBlack,
            focusedContainerColor = SurfaceGray,
            unfocusedContainerColor = SurfaceGray,
            focusedLeadingIconColor = TextGray,
            unfocusedLeadingIconColor = TextGray,
            focusedTrailingIconColor = TextGray,
            unfocusedTrailingIconColor = TextGray
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = TextBlack
        ),
        singleLine = true
    )
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}