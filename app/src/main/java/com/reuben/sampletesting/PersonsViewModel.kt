package com.reuben.sampletesting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.reuben.sampletesting.data.NoPersonsFoundException
import com.reuben.sampletesting.data.PeopleRepository
import com.reuben.sampletesting.data.Person
import com.reuben.sampletesting.data.Result
import com.reuben.sampletesting.data.asResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PersonsViewModel(
    private val repository: PeopleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var _uiState: MutableStateFlow<PersonsUIState> = MutableStateFlow(PersonsUIState.Idle)
    val uiState = _uiState.asStateFlow()

    fun getPeople() {

        viewModelScope.launch(dispatcher) {
            _uiState.value = PersonsUIState.Loading

            peopleStream.collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = PersonsUIState.People(result.data)
                    }
                    is Result.Error -> {
                        val message = when (result.exception) {
                            is NoPersonsFoundException -> {
                                result.exception.errorMessage
                            }
                            else -> {
                                result.exception?.message.orEmpty()
                            }
                        }

                        _uiState.value = PersonsUIState.Error(message)

                    }
                }
            }


        }
    }


    private val peopleStream = flow {
        emit(repository.getPeople())
    }.flowOn(dispatcher).asResult()

    @Suppress("UNCHECKED_CAST")
    class PersonsFactory(private val repository: PeopleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PersonsViewModel(repository) as T
        }
    }
}

sealed interface PersonsUIState {
    object Idle : PersonsUIState
    object Loading : PersonsUIState
    data class People(val data: List<Person>) : PersonsUIState
    data class Error(val errorMessage: String) : PersonsUIState
}

