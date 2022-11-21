package pro.yakuraion.englishhelper.commonui.di

import androidx.compose.runtime.staticCompositionLocalOf
import pro.yakuraion.englishhelper.domain.di.UseCasesProvider

val LocalUseCasesProvider = staticCompositionLocalOf<UseCasesProvider?> { null }
