package com.example.softcare.ui.theme

import androidx.compose.ui.graphics.Color

// NOVA PALETA: Teal e Amber

// Modo Claro
val TealPrimaryLight = Color(0xFF00897B)
val AmberSecondaryLight = Color(0xFFFFC107)
val BackgroundLightNew = Color(0xFFFAFAFA)
val SurfaceLightNew = Color(0xFFFFFFFF)
val ErrorLightNew = Color(0xFFE57373)
val TextPrimaryLightNew = Color(0xFF212121)
val TextSecondaryLightNew = Color(0xFF757575)
val InactiveLightNew = Color(0xFFE0E0E0)
val OnPrimaryLight = Color.White // Texto sobre a cor primária clara
val OnSecondaryLight = Color.Black // Texto sobre a cor secundária clara
val OnErrorLightColor = Color.White // Texto sobre cor de erro clara

// Modo Escuro
val TealPrimaryDark = Color(0xFF4DB6AC)
val AmberSecondaryDark = Color(0xFFFFA000)
val BackgroundDarkNew = Color(0xFF212121)
val SurfaceDarkNew = Color(0xFF303030)
val ErrorDarkNew = Color(0xFFD32F2F)
val TextPrimaryDarkNew = Color(0xFFFFFFFF)
val TextSecondaryDarkNew = Color(0xFFBDBDBD)
val InactiveDarkNew = Color(0xFF616161)
val OnPrimaryDark = Color.Black // Texto sobre a cor primária escura
val OnSecondaryDark = Color.Black // Texto sobre a cor secundária escura
val OnErrorDarkColor = Color.Black // Texto sobre cor de erro escura

// Cores para surfaceVariant e primaryContainer, que podem ser usadas
val SurfaceVariantLight = Color(0xFFECECEC) // Um pouco mais escuro que o surface para contraste
val OnSurfaceVariantLight = TextPrimaryLightNew
val PrimaryContainerLight = TealPrimaryLight.copy(alpha = 0.1f) // Uma versão mais clara/transparente da primária
val OnPrimaryContainerLight = TealPrimaryLight

val SurfaceVariantDark = Color(0xFF3C3C3C) // Um pouco mais claro que o surface escuro
val OnSurfaceVariantDark = TextPrimaryDarkNew
val PrimaryContainerDark = TealPrimaryDark.copy(alpha = 0.2f)
val OnPrimaryContainerDark = TealPrimaryDark