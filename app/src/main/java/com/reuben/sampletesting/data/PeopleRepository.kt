package com.reuben.sampletesting.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface PeopleRepository {
    suspend fun getPeople(): List<Person>
}

class PeopleRepositoryImpl(
    private val api: PeopleApi, private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PeopleRepository {
    override suspend fun getPeople(): List<Person> {
        return withContext(dispatcher) {
            api.getPeople()
        }
    }
}

abstract class MockPeopleRepository(
    private val dispatcher: CoroutineDispatcher
) : PeopleRepository

class MockSuccessPeopleRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MockPeopleRepository(dispatcher) {
    override suspend fun getPeople(): List<Person> {
        return withContext(dispatcher) {
            samplePeople
        }
    }

}

class MockFailureRepository(
    dispatcher: CoroutineDispatcher = Dispatchers.IO, val errorMessage: String
) : MockPeopleRepository(dispatcher) {
    override suspend fun getPeople(): List<Person> {
        throw NoPersonsFoundException(errorMessage)
    }

}

interface PeopleApi {
    suspend fun getPeople(): List<Person>
}

class PeopleApiImpl : PeopleApi {
    override suspend fun getPeople(): List<Person> = samplePeople
}