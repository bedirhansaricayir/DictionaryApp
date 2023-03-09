package com.dictionary.android.feature_dictionary.data.local

import androidx.room.*
import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(word: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity WHERE word =:word")
    suspend fun deleteWordToFavorite(word: String)

    @Query("SELECT * FROM FavoriteEntity")
    fun getWordsFromFavorite(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS (SELECT * FROM FavoriteEntity WHERE word=:word)")
    suspend fun isAvailableInDb(word: String): Boolean
}