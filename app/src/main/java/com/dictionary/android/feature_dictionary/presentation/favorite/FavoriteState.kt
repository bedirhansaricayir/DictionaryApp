package com.dictionary.android.feature_dictionary.presentation.favorite

import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity

data class FavoriteState(
    val favoriteItems: List<FavoriteEntity> = emptyList(),
    val isLoading: Boolean = false
)