package com.dictionary.android.feature_dictionary.domain.use_case

import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import com.dictionary.android.feature_dictionary.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BaseFavoriteRoomUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend fun insert(word: FavoriteEntity) {
        return repository.insertFavorite(word)
    }

    suspend fun delete(word: String) {
        return repository.deleteFavorite(word)
    }

     fun getAll(): Flow<List<FavoriteEntity>> {
        return repository.getWordsFromFavorite()
    }

    suspend fun isAvailableInDb(word: String): Boolean {
        return repository.isAvailableInDb(word)
    }
}