package com.example.playlist_maker_android_sabitovratmir

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

@Composable
fun SettingsScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(false) }

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
                text = stringResource(R.string.settings),
                color = Color(0xFF1A1B22),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 66.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(61.dp)
                    .clickable { isDarkTheme = !isDarkTheme }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.dark_theme_title),
                            fontSize = 16.sp,
                            color = Color(0xFF1A1B22)
                        )
                    }

                    CustomSwitch(
                        checked = isDarkTheme,
                        onCheckedChange = { isDarkTheme = it }
                    )
                }
            }

            SettingsSection(
                title = stringResource(R.string.share_app),
                iconRes = R.drawable.share,
                onClick = { shareApp(context) }
            )


            SettingsSection(
                title = stringResource(R.string.write_to_developers),
                iconRes = R.drawable.help,
                onClick = { writeToDevelopers(context) }
            )

            SettingsSection(
                title = stringResource(R.string.user_agreement),
                iconRes = R.drawable.keyboardarrowright,
                onClick = { openUserAgreement(context) }
            )
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = Color(0xFF1A1B22)
                )
            }
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color(0xFFAEAFB4)
            )
        }
    }
}

private fun shareApp(context: Context) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_app_text))
    }

    val chooserIntent = Intent.createChooser(
        shareIntent,
        context.getString(R.string.share_app)
    )

    ContextCompat.startActivity(context, chooserIntent, null)

}

private fun writeToDevelopers(context: Context) {
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.developer_email)))
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject))
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.email_body))
    }

    ContextCompat.startActivity(context, emailIntent, null)
}

private fun openUserAgreement(context: Context) {
    val agreementUrl = context.getString(R.string.user_agreement_url)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(agreementUrl))

    ContextCompat.startActivity(context, intent, null)
}

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val thumbPosition by animateDpAsState(
        targetValue = if (checked) 16.dp else 0.dp,
        animationSpec = tween(durationMillis = 200)
    )

    val trackColor by animateColorAsState(
        targetValue = if (checked) Color(0xFF3772E7).copy(alpha = 0.48f) else Color(0xFFE6E8EB),
        animationSpec = tween(durationMillis = 200)
    )

    val thumbColor by animateColorAsState(
        targetValue = if (checked) Color(0xFF3772E7) else Color(0xFFAEAFB4),
        animationSpec = tween(durationMillis = 200)
    )

    Box(
        modifier = modifier
            .width(35.dp)
            .height(40.dp)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(12.dp)
                .background(trackColor, RoundedCornerShape(6.dp))
        )

        Box(
            modifier = Modifier
                .size(20.dp)
                .offset(x = thumbPosition)
                .background(thumbColor, CircleShape)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}