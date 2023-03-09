package com.dictionary.android.feature_dictionary.domain.repository

import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun insertFavorite(word: FavoriteEntity)

    suspend fun deleteFavorite(word: String)

     fun getWordsFromFavorite(): Flow<List<FavoriteEntity>>

     suspend fun isAvailableInDb(word: String): Boolean
}