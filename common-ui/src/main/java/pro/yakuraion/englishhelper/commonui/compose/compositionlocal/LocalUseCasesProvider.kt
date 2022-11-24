package pro.yakuraion.englishhelper.commonui.compose.compositionlocal

import androidx.compose.runtime.staticCompositionLocalOf
import pro.yakuraion.englishhelper.domain.di.UseCasesProvider

val LocalUseCasesProvider = staticCompositionLocalOf<UseCasesProvider?> { null }
