package com.dictionary.android.feature_dictionary.domain.repository

import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity

interface FavoriteRepository {

    suspend fun insertFavorite(word: FavoriteEntity)

    suspend fun deleteFavorite(word: String)

    suspend fun getWordsFromFavorite(): List<FavoriteEntity>
}