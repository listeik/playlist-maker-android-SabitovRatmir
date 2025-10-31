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
import com.example.playlist_maker_android_sabitovratmir.data.network.Track
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
                    tint = Color(0xFF1A1B22)
                )
            }

            Text(
                text = stringResource(R.string.search_screen_text),
                color = Color(0xFF1A1B22),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 66.dp)
            )
        }

        SearchTextField(
            searchText = searchText,
            onSearchTextChange = {
                searchText = it
                if (it.isNotEmpty()) {
                    viewModel.search(it)
                }
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
                            color = Color(0xFFAEAFB4),
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
                        color = Color(0xFF1A1B22)
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
                            color = Color(0xFFAEAFB4),
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
                            HorizontalDivider(thickness = 0.5.dp)
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
                            color = Color(0xFF1A1B22),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error,
                            color = Color(0xFFAEAFB4),
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
    onSearchTextChange: (String) -> Unit
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
                color = Color(0xFFAEAFB4),
                fontSize = 18.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_screen_text),
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFAEAFB4)
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSearchTextChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear_icon_description),
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFFAEAFB4)
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFE6E8EB),
            unfocusedBorderColor = Color(0xFFE6E8EB),
            focusedTextColor = Color(0xFF1A1B22),
            unfocusedTextColor = Color(0xFF1A1B22),
            cursorColor = Color(0xFF1A1B22),
            focusedContainerColor = Color(0xFFE6E8EB),
            unfocusedContainerColor = Color(0xFFE6E8EB),
            focusedLeadingIconColor = Color(0xFFAEAFB4),
            unfocusedLeadingIconColor = Color(0xFFAEAFB4),
            focusedTrailingIconColor = Color(0xFFAEAFB4),
            unfocusedTrailingIconColor = Color(0xFFAEAFB4)
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = Color(0xFF1A1B22)
        ),
        singleLine = true
    )
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}