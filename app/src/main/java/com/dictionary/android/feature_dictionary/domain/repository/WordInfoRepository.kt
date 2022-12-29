package com.dictionary.android.feature_dictionary.domain.repository

import com.dictionary.android.core.util.Resource
import com.dictionary.android.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}