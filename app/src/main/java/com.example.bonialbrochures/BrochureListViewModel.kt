package com.example.bonialbrochures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bonialbrochures.data.local.model.BrochureEntity
import com.example.bonialbrochures.domain.usecase.BrochureUseCase
import com.example.bonialbrochures.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrochureListViewModel @Inject constructor(
    private val brochureUseCase: BrochureUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BrochureListState())
    val state: StateFlow<BrochureListState> = _state
    private var brochures = listOf<BrochureEntity>()


    private fun getBrochureList() {
        viewModelScope.launch {
            brochureUseCase.getBrochureList()
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    _state.value = BrochureListState(
                        error = error.message ?: "An unexpected error occurred"
                    )
                }
                .collect { handleUseCaseResponses(it) }


        }
    }

    fun getFilteredBrochures() {
        viewModelScope.launch {
            brochureUseCase.getFilteredBrochureList()
                .flowOn(Dispatchers.IO)
                .collect { handleUseCaseResponses(it) }
        }
    }

    private fun handleUseCaseResponses(result: Resource<List<BrochureEntity>>) {
        when (result) {
            is Resource.Success -> {
                _state.value =
                    BrochureListState(brochureList = result.data ?: emptyList())
                brochures = result.data ?: emptyList()
            }
            is Resource.Error -> {
                _state.value = BrochureListState(
                    error = result.message ?: "An unexpected error occurred"
                )
            }
            is Resource.Loading -> {
                _state.value = BrochureListState(isLoading = true)
            }
        }
    }

}
