package com.arif.gatchafood

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

val images = arrayOf(
    R.drawable.baked_goods_1,
    R.drawable.baked_goods_2,
    R.drawable.baked_goods_3
)
val imageDescriptions = arrayOf(
    R.string.image1_description,
    R.string.image2_description,
    R.string.image3_description
)

@Composable
fun BakingScreen(
    bakingViewModel: BakingViewModel = viewModel()
) {
    val selectedImage = remember { mutableIntStateOf(0) }
    val defaultPlace = stringResource(R.string.prompt_placeholder)
    var place by rememberSaveable { mutableStateOf(defaultPlace) }
    val placeholderResult = jsonModel
    var prompt by rememberSaveable { mutableStateOf("") }
    val uiState by bakingViewModel.uiState.collectAsState()
    val filteruiState by bakingViewModel.filteruiState.collectAsState()
    val filter by bakingViewModel.filter.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.baking_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        var showList by remember { mutableStateOf(true) }

        Row(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            TextField(
                value = prompt,
                label = { Text(stringResource(R.string.label_prompt)) },
                onValueChange = { prompt = it },
                modifier = Modifier
                    .weight(0.8f)
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically)
            )

            Button(
                onClick = {
                    bakingViewModel.sendPrompt(prompt)
                    showList = true
                },
                enabled = prompt.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = stringResource(R.string.action_go))
            }
        }

    if (uiState is UiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            var textColor = MaterialTheme.colorScheme.onSurface
            if (uiState is UiState.Error) {
                textColor = MaterialTheme.colorScheme.error
//                result = (uiState as UiState.Error).errorMessage
            } else if (uiState is UiState.Success) {
                textColor = MaterialTheme.colorScheme.onSurface
//                result = (uiState as UiState.Success).outputText
                val scrollState = rememberScrollState()
                val restaurants = (uiState as UiState.Success).outputText
                var restaurantName by rememberSaveable { mutableStateOf("") }
                val coroutineScope = rememberCoroutineScope()

                val judulArrayList = arrayListOf<String>()
                restaurants.forEach { restaurant ->
                    judulArrayList.add(restaurant.judul.toString())
                }

                Button(onClick = {
                    val durationInSecond = 5
                    val job = coroutineScope.launch {
                        while (isActive) {
                            restaurantName = judulArrayList.random()
                            bakingViewModel.filter(restaurantName)
                            delay(100)
                        }
                    }

                    coroutineScope.launch {
                        delay(durationInSecond * 1000L)
                        job.cancel()
                    }
                }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "Pilihin aku dong!")
                }

                if (filteruiState is UiState.Success) {
                    val restaurants = (filteruiState as UiState.Success).outputText
                    if (restaurants.isNotEmpty()) {
                        RestaurantListItem(restaurants[0], place)
                        showList = false
                    }
                }

                AnimatedVisibility(
                    visible = showList,
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(restaurants) { index, image ->
                            RestaurantListItem(image, place)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantListItem(restaurant: RestaurantResponseItem, place: String) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        onClick = { expanded = !expanded },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = restaurant.judul.toString(),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(text = restaurant.alamat.toString(), style = MaterialTheme.typography.bodyMedium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = restaurant.rating.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Text(
                        text = "(${restaurant.jumlahUlasan})",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Text(
                    text = restaurant.rangeHarga.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            AnimatedVisibility(
                visible = expanded,
            ) {
                Column {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Column {
                        restaurant.ringkasan?.forEach { summaryPoint ->
                            Text(
                                text = "- $summaryPoint",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    val uriHandler = LocalUriHandler.current
                    Button(onClick = {
                        uriHandler.openUri("https://www.google.com/maps/search/${restaurant.judul} $place")
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Meluncur ke lokasi")
                    }
                }
            }
        }
    }
}