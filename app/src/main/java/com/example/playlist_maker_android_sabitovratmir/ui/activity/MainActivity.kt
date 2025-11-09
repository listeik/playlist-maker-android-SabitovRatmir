package com.example.playlist_maker_android_sabitovratmir.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_sabitovratmir.R
import com.example.playlist_maker_android_sabitovratmir.ui.theme.PlaylistmakerandroidSabitovRatmirTheme
import com.example.playlist_maker_android_sabitovratmir.ui.theme.PrimaryBlue
import com.example.playlist_maker_android_sabitovratmir.ui.theme.TextBlack
import com.example.playlist_maker_android_sabitovratmir.ui.theme.TextGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistmakerandroidSabitovRatmirTheme {
                PlaylistHost()
            }
        }
    }
}

@Composable
fun MainScreen(onSearchClick: () -> Unit = {},
               onSettingsClick: () -> Unit = {}) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBlue)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(PrimaryBlue),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(R.string.playlist_maker),
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.White)
                .padding(top = 8.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {

                MenuCard(
                    iconRes = R.drawable.search,
                    text = stringResource(R.string.search),
                    onClick = onSearchClick
                )


                MenuCard(
                    iconRes = R.drawable.playlist,
                    text = stringResource(R.string.playlist),
                    onClick = {
                        //пусто, так как в задании ведь не сказано создать класс
                    }
                )


                MenuCard(
                    iconRes = R.drawable.heart,
                    text = stringResource(R.string.favorites),
                    onClick = {
                        //пусто, так как в задании ведь не сказано создать класс
                    }
                )
                MenuCard(
                    iconRes = R.drawable.settings,
                    text = stringResource(R.string.settings),
                    onClick = onSettingsClick
                )
            }
        }
    }
}

@Composable
fun MenuCard(
    iconRes: Int,
    text: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(19.dp),
                tint = TextBlack
            )

            Spacer(modifier = Modifier.width(16.dp))


            Text(
                text = text,
                color = TextBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )


            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.navigate_icon_description),
                modifier = Modifier.size(24.dp),
                tint = TextGray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    PlaylistmakerandroidSabitovRatmirTheme {
        MainScreen()
    }
}