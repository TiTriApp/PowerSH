package akram.bensalem.powersh.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import akram.bensalem.powersh.data.model.ShoeProduct

suspend fun List<ShoeProduct>.getChipNamesList(): List<String> {
    return withContext(Dispatchers.Default) {
        val collection = mutableSetOf<String>()
        this@getChipNamesList.forEach { thinkpad ->
            collection.add("T Series")
        }
        collection.toList()
    }
}
