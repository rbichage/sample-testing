package com.reuben.sampletesting

import com.reuben.sampletesting.data.PeopleApi
import com.reuben.sampletesting.data.PeopleRepository
import com.reuben.sampletesting.data.PeopleRepositoryImpl
import com.reuben.sampletesting.data.samplePeople
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PersonsRepositoryTests {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var peopleRepository: PeopleRepository

    private val peopleApi: PeopleApi = mockk(relaxed = true)

    @Before
    fun setup() {

        Dispatchers.setMain(testDispatcher)
        peopleRepository = PeopleRepositoryImpl(
            api = peopleApi, dispatcher = testDispatcher
        )
    }

    @Test
    fun `test returns success`() = runTest {
        coEvery {
            peopleApi.getPeople()
        } returns samplePeople

        val response = peopleRepository.getPeople()
        assertThat(response[1].firstName, `is`(samplePeople[1].firstName))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}