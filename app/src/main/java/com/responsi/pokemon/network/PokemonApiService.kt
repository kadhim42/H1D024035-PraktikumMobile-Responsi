package com.responsi.pokemon.network

import com.responsi.pokemon.data.model.PokemonDetail
import com.responsi.pokemon.data.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 151
    ): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): PokemonDetail
}