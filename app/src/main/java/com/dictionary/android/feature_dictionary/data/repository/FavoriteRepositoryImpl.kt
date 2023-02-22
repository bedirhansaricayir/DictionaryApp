package com.dictionary.android.feature_dictionary.data.repository

import com.dictionary.android.feature_dictionary.data.local.FavoriteWordDao
import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import com.dictionary.android.feature_dictionary.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val dao: FavoriteWordDao
): FavoriteRepository{
    override suspend fun insertFavorite(word: FavoriteEntity) = dao.insertFavorite(word)


    override suspend fun deleteFavorite(word: String) = dao.deleteWordToFavorite(word)


    override suspend fun getWordsFromFavorite(): List<FavoriteEntity>  = dao.getWordsFromFavorite()

}