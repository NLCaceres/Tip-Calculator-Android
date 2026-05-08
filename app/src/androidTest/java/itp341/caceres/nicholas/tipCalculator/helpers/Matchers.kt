package itp341.caceres.nicholas.tipCalculator.helpers

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.test.SemanticsMatcher

fun hasKey(key: SemanticsPropertyKey<*>) = SemanticsMatcher.keyIsDefined(key)