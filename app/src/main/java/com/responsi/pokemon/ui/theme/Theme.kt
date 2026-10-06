package com.responsi.pokemon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PokeRedLight,
    onPrimary = Color(0xFF690005),
    secondary = PokeBlueLight,
    tertiary = PokeYellowLight
)

private val LightColorScheme = lightColorScheme(
    primary = PokeRed,
    secondary = PokeBlue,
    tertiary = PokeYellow
)

@Composable
fun PokemonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}