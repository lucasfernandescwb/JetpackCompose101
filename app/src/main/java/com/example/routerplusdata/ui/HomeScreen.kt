package com.example.routerplusdata.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.routerplusdata.viewmodel.AnimeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (String) -> Unit
) {
    val viewModel: AnimeViewModel = viewModel()

    if (viewModel.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.animes) { anime ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            onNavigateToDetails(anime.id)
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {

                        Log.d(
                            "KITSU",
                            anime.attributes.coverImage.toString()
                        )

                        AsyncImage(
                            model = anime.attributes.coverImage?.small,
                            contentDescription = anime.attributes.canonicalTitle,
                            modifier = Modifier
                                .width(120.dp)
                                .height(170.dp),
                            contentScale = ContentScale.Crop,
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = anime.attributes.canonicalTitle,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text(
                                text = anime.attributes.synopsis,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}