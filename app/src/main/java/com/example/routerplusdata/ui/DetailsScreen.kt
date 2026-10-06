package com.example.routerplusdata.ui

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.routerplusdata.utils.formatDate
import com.example.routerplusdata.viewmodel.SingleAnimeViewModel

@Composable
fun DetailsScreen(
    onBackClick: () -> Unit,
    animeId: String?
) {

    val viewModel: SingleAnimeViewModel = viewModel()

    if (animeId.isNullOrEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Invalid Anime ID")

            Button(
                onClick = onBackClick
            ) {
                Text("Go back")
            }
        }

        return
    }

    LaunchedEffect(animeId) {
        viewModel.getSingleAnime(animeId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {

        if (viewModel.isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {

            val anime = viewModel.anime

            if (anime != null) {

                Box {

                    AsyncImage(
                        model = anime.attributes.coverImage?.large,
                        contentDescription = anime.attributes.canonicalTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .blur(10.dp),
                        contentScale = ContentScale.FillBounds,
                    )

                    AsyncImage(
                        model = anime.attributes.coverImage?.small,
                        contentDescription = anime.attributes.canonicalTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .align(Alignment.Center)
                            .shadow(8.dp),
                        contentScale = ContentScale.FillHeight
                    )

                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.45f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = anime.attributes.status.replaceFirstChar { it.uppercase() },
                                color = Color.White,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(
                                    horizontal = 10.dp,
                                    vertical = 6.dp
                                )
                            )
                        }

                        Surface(
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            anime.attributes.startDate?.let {
                                Text(
                                    text = formatDate(it),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 6.dp
                                    )
                                )
                            }
                        }

                        if (!anime.attributes.endDate.isNullOrEmpty()) {
                            Surface(
                                color = Color.DarkGray,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = formatDate(anime.attributes.endDate),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 6.dp
                                    )
                                )
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                    Text(
                        text = anime.attributes.canonicalTitle,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = anime.attributes.synopsis,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }

            } else {

                Text("Error when loading anime infos.")

                Button(
                    onClick = onBackClick
                ) {
                    Text("Go Back")
                }
            }
        }
    }
}