package com.dictionary.android.feature_dictionary.domain.use_case

import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import com.dictionary.android.feature_dictionary.domain.repository.FavoriteRepository
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

    suspend fun getAll(): List<FavoriteEntity> {
        return repository.getWordsFromFavorite()
    }
}