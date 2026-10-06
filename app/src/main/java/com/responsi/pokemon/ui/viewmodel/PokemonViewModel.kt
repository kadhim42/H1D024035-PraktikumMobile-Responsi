package com.responsi.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.responsi.pokemon.data.model.PokemonDetail
import com.responsi.pokemon.data.model.PokemonListItem
import com.responsi.pokemon.data.repository.PokemonRepository
import com.responsi.pokemon.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class PokemonViewModel(
    private val repository: PokemonRepository = PokemonRepository(ApiClient.service)
) : ViewModel() {

    private val _listState =
        MutableStateFlow<UiState<List<PokemonListItem>>>(UiState.Loading)
    val listState: StateFlow<UiState<List<PokemonListItem>>> = _listState.asStateFlow()

    private val _detailState =
        MutableStateFlow<UiState<PokemonDetail>>(UiState.Loading)
    val detailState: StateFlow<UiState<PokemonDetail>> = _detailState.asStateFlow()

    // Dipanggil otomatis saat ViewModel pertama kali dibuat
    init {
        loadPokemonList()
    }

    fun loadPokemonList() {
        viewModelScope.launch {
            _listState.value = UiState.Loading
            try {
                _listState.value = UiState.Success(repository.getPokemonList())
            } catch (e: Exception) {
                _listState.value = UiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun loadPokemonDetail(id: Int) {
        viewModelScope.launch {
            _detailState.value = UiState.Loading
            try {
                _detailState.value = UiState.Success(repository.getPokemonDetail(id))
            } catch (e: Exception) {
                _detailState.value = UiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}