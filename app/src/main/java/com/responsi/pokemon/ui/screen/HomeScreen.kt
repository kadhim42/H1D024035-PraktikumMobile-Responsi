package com.responsi.pokemon.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.responsi.pokemon.data.model.PokemonListItem
import com.responsi.pokemon.data.model.id
import com.responsi.pokemon.ui.components.PokemonItemCard
import com.responsi.pokemon.ui.viewmodel.PokemonViewModel
import com.responsi.pokemon.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PokemonViewModel,
    onPokemonClick: (Int) -> Unit
) {
    // State dari ViewModel.
    val uiState by viewModel.listState.collectAsState()

    // State lokal untuk teks search.
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokédex") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // UI berubah sesuai state
            when (val state = uiState) {
                is UiState.Loading -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Memuat data...")
                    }
                }

                is UiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadPokemonList() }) {
                            Text("Coba Lagi")
                        }
                    }
                }

                is UiState.Success -> {
                    // Filter dihitung ulang hanya kalau data atau teks search berubah
                    val filtered = remember(state.data, searchQuery) {
                        state.data.filter {
                            it.name.contains(searchQuery.trim(), ignoreCase = true)
                        }
                    }

                    PokemonList(
                        pokemon = filtered,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        onPokemonClick = onPokemonClick
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonList(
    pokemon: List<PokemonListItem>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onPokemonClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Cari Pokémon...") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        if (pokemon.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Pokémon tidak ditemukan.")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(pokemon, key = { it.name }) { item ->
                    PokemonItemCard(
                        pokemon = item,
                        onClick = { onPokemonClick(item.id) }
                    )
                }
            }
        }
    }
}