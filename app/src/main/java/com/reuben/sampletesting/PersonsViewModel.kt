package com.reuben.sampletesting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.reuben.sampletesting.data.PeopleRepository
import com.reuben.sampletesting.data.Person
import com.reuben.sampletesting.data.Result
import com.reuben.sampletesting.data.asResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PersonsViewModel(
    private val repository: PeopleRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var _uiState: MutableStateFlow<PersonsUIState> = MutableStateFlow(PersonsUIState.Idle)
    val uiState: StateFlow<PersonsUIState>
        get() = _uiState

    fun getPeople() {
        viewModelScope.launch(dispatcher) {
            _uiState.value = PersonsUIState.Idle

            peopleStream.collect { result ->
                Log.e("VIEWMODEL: result is ", "$result")
                when (result) {
                    is Result.Success -> {
                        delay(1000L)
                        _uiState.value = PersonsUIState.People(result.data)
                    }
                    is Result.Error -> {
                        _uiState.value = PersonsUIState.Error(result.exception?.message.orEmpty())
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
    data class People(val data: List<Person>) : PersonsUIState
    data class Error(val errorMessage: String) : PersonsUIState
}

