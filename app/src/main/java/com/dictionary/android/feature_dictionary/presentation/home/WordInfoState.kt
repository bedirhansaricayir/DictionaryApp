package com.dictionary.android.feature_dictionary.presentation.home

import com.dictionary.android.feature_dictionary.domain.model.WordInfo

data class WordInfoState(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)
