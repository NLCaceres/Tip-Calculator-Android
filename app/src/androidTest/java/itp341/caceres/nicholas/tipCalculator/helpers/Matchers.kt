package itp341.caceres.nicholas.tipCalculator.helpers

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.test.SemanticsMatcher

fun hasKey(key: SemanticsPropertyKey<*>) = SemanticsMatcher.keyIsDefined(key)

fun hasRole(role: Role) = SemanticsMatcher.expectValue(SemanticsProperties.Role, role)
