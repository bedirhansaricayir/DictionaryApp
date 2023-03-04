package com.dictionary.android.feature_dictionary.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.dictionary.android.feature_dictionary.data.local.Converters
import com.dictionary.android.feature_dictionary.data.local.WordInfoDatabase
import com.dictionary.android.feature_dictionary.data.local.datastore.DataStoreRepository
import com.dictionary.android.feature_dictionary.data.remote.DictionaryApi
import com.dictionary.android.feature_dictionary.data.repository.FavoriteRepositoryImpl
import com.dictionary.android.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.dictionary.android.feature_dictionary.data.util.GsonParser
import com.dictionary.android.feature_dictionary.domain.repository.FavoriteRepository
import com.dictionary.android.feature_dictionary.domain.repository.WordInfoRepository
import com.dictionary.android.feature_dictionary.domain.use_case.BaseFavoriteRoomUseCase
import com.dictionary.android.feature_dictionary.domain.use_case.GetWordInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(repository)
    }

    @Provides
    @Singleton
    fun provideBaseFavoriteRoomUseCase(repository: FavoriteRepository): BaseFavoriteRoomUseCase {
        return BaseFavoriteRoomUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: WordInfoDatabase,
        api: DictionaryApi
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        db: WordInfoDatabase
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(db.favoriteDao)
    }
    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app, WordInfoDatabase::class.java, "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson()))).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)
}