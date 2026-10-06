package com.responsi.pokemon.data.model

import com.google.gson.annotations.SerializedName

// Response dari endpoint list: /pokemon
data class PokemonListResponse(
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String
)

// Response dari endpoint detail: /pokemon/{id}
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val stats: List<StatSlot>
)

data class TypeSlot(
    val type: NamedResource
)

data class StatSlot(
    @SerializedName("base_stat") val baseStat: Int,
    val stat: NamedResource
)

data class NamedResource(
    val name: String
)
val PokemonListItem.id: Int
    get() = url.trimEnd('/').substringAfterLast('/').toIntOrNull() ?: 0

// URL gambar dibentuk dari ID
fun Int.toImageUrl(): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$this.png"

// "pikachu" -> "Pikachu"
fun String.toDisplayName(): String =
    replaceFirstChar { it.uppercase() }