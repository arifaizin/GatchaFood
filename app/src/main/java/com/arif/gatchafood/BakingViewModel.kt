package com.arif.gatchafood

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BakingViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val _filteruiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val filteruiState: StateFlow<UiState> =
        _filteruiState.asStateFlow()

    private val _filter: MutableStateFlow<String> =
        MutableStateFlow("")
    val filter: StateFlow<String> =
        _filter.asStateFlow()

    init {
        val gson = Gson()
        val restaurantListType = object : TypeToken<List<RestaurantResponseItem>>() {}.type
        val restaurants: List<RestaurantResponseItem> =
            gson.fromJson(jsonModel, restaurantListType)
        _uiState.value = UiState.Success(restaurants)
    }

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.apiKey
    )

    fun filter(name: String) {
        _filter.value = name
        _filteruiState.value =
            UiState.Success((_uiState.value as UiState.Success).outputText.filter {
                it.judul == name
            })
    }

    fun sendPrompt(
        type: String = "",
        place: String = "Bandung"
    ) {
        _filteruiState.value = UiState.Initial
        _uiState.value = UiState.Loading

        val prompt = """
Berikan saya 10 rekomendasi restoran $type makanan di $place dalam format json object yang berisi field 
judul, alamat, jumlah ulasan, rating, range harga, dan ringkasan. Please provide a response in a structured JSON format that matches the following model: $jsonModel'
        """.trimIndent()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(prompt)
                response.text?.let { outputContent ->
                    Log.d("outputContent", outputContent)

                    val gson = Gson()
                    val restaurantListType =
                        object : TypeToken<List<RestaurantResponseItem>>() {}.type
                    val restaurants: List<RestaurantResponseItem> =
                        gson.fromJson(outputContent, restaurantListType)

                    _uiState.value = UiState.Success(restaurants)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }
}