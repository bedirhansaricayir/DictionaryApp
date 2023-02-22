package com.dictionary.android.feature_dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import com.dictionary.android.feature_dictionary.data.local.entity.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class,FavoriteEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase: RoomDatabase() {

    abstract val dao: WordInfoDao

    abstract val favoriteDao: FavoriteWordDao
}