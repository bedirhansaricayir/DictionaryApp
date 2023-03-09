package com.dictionary.android.feature_dictionary.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dictionary.android.feature_dictionary.domain.use_case.BaseFavoriteRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val roomUseCase: BaseFavoriteRoomUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState(isLoading = false))
    val state: StateFlow<FavoriteState> = _state.asStateFlow()

    private var job: Job? = null

    init {
        getAllFromFavorite()
    }

    fun getAllFromFavorite() {
        job?.cancel()
        job = viewModelScope.launch {
            roomUseCase.getAll().onEach { data ->
                _state.update {
                    it.copy(favoriteItems = data, isLoading = false)
                }
            }.collect()
        }
    }

    fun removeFromFavorite(word: String) {
        viewModelScope.launch {
            roomUseCase.delete(word)
        }
    }
}
