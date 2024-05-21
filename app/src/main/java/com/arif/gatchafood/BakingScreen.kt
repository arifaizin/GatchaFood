package com.arif.gatchafood

import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    val placeholderPrompt = stringResource(R.string.prompt_placeholder)
    val placeholderResult = jsonModel
    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }
    var result by rememberSaveable { mutableStateOf(placeholderResult) }
    val uiState by bakingViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.baking_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

//        LazyRow(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            itemsIndexed(images) { index, image ->
//                var imageModifier = Modifier
//                    .padding(start = 8.dp, end = 8.dp)
//                    .requiredSize(200.dp)
//                    .clickable {
//                        selectedImage.intValue = index
//                    }
//                if (index == selectedImage.intValue) {
//                    imageModifier =
//                        imageModifier.border(BorderStroke(4.dp, MaterialTheme.colorScheme.primary))
//                }
//                Image(
//                    painter = painterResource(image),
//                    contentDescription = stringResource(imageDescriptions[index]),
//                    modifier = imageModifier
//                )
//            }
//        }

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
//                    val bitmap = BitmapFactory.decodeResource(
//                        context.resources,
//                        images[selectedImage.intValue]
//                    )
                    bakingViewModel.sendPrompt(prompt)
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
                result = (uiState as UiState.Success).outputText
            }
            val scrollState = rememberScrollState()

            val gson = Gson()
            val restaurantListType = object : TypeToken<List<RestaurantResponseItem>>() {}.type
            val restaurants: List<RestaurantResponseItem> =
                gson.fromJson(result, restaurantListType)

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
                        delay(100)
                    }
                }

                coroutineScope.launch {
                    delay(durationInSecond * 1000L)

                    job.cancel()
                }
            }) {
                Text(text = "Pilihin aku dong!")
            }

            Text(
                text = restaurantName,
                textAlign = TextAlign.Start,
                color = textColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(restaurants) { index, image ->
//                    var imageModifier = Modifier
//                        .padding(start = 8.dp, end = 8.dp)
//                        .requiredSize(200.dp)
//                        .clickable {
//                            selectedImage.intValue = index
//                        }
//                    if (index == selectedImage.intValue) {
//                        imageModifier =
//                            imageModifier.border(
//                                BorderStroke(
//                                    4.dp,
//                                    MaterialTheme.colorScheme.primary
//                                )
//                            )
//                    }
//                    Image(
//                        painter = painterResource(image),
//                        contentDescription = stringResource(imageDescriptions[index]),
//                        modifier = imageModifier
//                    )
//                    Text(text = image.judul.toString())
                    RestaurantListItem(image)
                }
            }
//            Text(
//                text = result,
//                textAlign = TextAlign.Start,
//                color = textColor,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(16.dp)
//                    .fillMaxSize()
//                    .verticalScroll(scrollState)
//            )
        }
    }
}

@Composable
fun RestaurantListItem(restaurant: RestaurantResponseItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp)
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
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Column {
                restaurant.ringkasan?.forEach { summaryPoint ->
                    Text(text = "- $summaryPoint", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}