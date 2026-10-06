package com.responsi.pokemon.data.repository

import com.responsi.pokemon.data.model.PokemonDetail
import com.responsi.pokemon.data.model.PokemonListItem
import com.responsi.pokemon.network.PokemonApiService

class PokemonRepository(private val api: PokemonApiService) {

    suspend fun getPokemonList(): List<PokemonListItem> =
        api.getPokemonList().results

    suspend fun getPokemonDetail(id: Int): PokemonDetail =
        api.getPokemonDetail(id)
}