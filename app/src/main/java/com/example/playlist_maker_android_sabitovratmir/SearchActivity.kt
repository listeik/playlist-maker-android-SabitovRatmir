package com.example.playlist_maker_android_sabitovratmir

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun SearchScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }


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
                    contentDescription = "Назад",
                    tint = Color(0xFF1A1B22)
                )
            }

            Text(
                text = "Поиск",
                color = Color(0xFF1A1B22),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 66.dp)
            )
        }

        SearchTextField(
            searchText = searchText,
            onSearchTextChange = { searchText = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                text = "Поиск",
                color = Color(0xFFAEAFB4),
                fontSize = 18.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Поиск",
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
                        contentDescription = "Очистить",
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