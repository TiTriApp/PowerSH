package akram.bensalem.powersh.data

import androidx.compose.runtime.Stable

@Stable
interface AutoCompleteEntity {
    fun filter(query: String): Boolean
}

@Stable
interface ValueAutoCompleteEntity<T> : AutoCompleteEntity {
    val value: T
}