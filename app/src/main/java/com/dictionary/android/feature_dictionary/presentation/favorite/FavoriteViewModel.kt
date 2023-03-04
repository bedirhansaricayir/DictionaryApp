package com.dictionary.android.feature_dictionary.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import com.dictionary.android.feature_dictionary.domain.use_case.BaseFavoriteRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val roomUseCase: BaseFavoriteRoomUseCase
): ViewModel() {

    var list: List<FavoriteEntity> = emptyList()

    init {
        viewModelScope.launch {
            list = getAllFromFavorite()
        }
    }

    suspend fun getAllFromFavorite(): List<FavoriteEntity> {
        return roomUseCase.getAll()
    }
}