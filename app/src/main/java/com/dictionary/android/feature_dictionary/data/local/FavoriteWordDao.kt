package com.dictionary.android.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import com.dictionary.android.feature_dictionary.data.local.entity.WordInfoEntity

@Dao
interface FavoriteWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(word: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity WHERE word =:word")
    suspend fun deleteWordToFavorite(word: String)

    @Query("SELECT * FROM FavoriteEntity")
    suspend fun getWordsFromFavorite(): List<FavoriteEntity>
}