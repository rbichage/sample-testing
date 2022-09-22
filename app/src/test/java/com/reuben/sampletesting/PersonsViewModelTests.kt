package com.reuben.sampletesting

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.reuben.sampletesting.data.MockFailureRepository
import com.reuben.sampletesting.data.MockSuccessPeopleRepository
import com.reuben.sampletesting.data.samplePeople
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
class PersonsViewModelTests {

    private lateinit var personsViewModel: PersonsViewModel

    private val testsDispatcher = StandardTestDispatcher()

    private lateinit var collectJob: Job


    @Before
    fun setup() {
        Dispatchers.setMain(testsDispatcher)
    }

    @Test
    fun `test getting people is successful`() = runTest {
        personsViewModel = PersonsViewModel(
            repository = MockSuccessPeopleRepository(
                testsDispatcher
            )
        )


        collectJob = launch(testsDispatcher) {
            personsViewModel.getPeople()
        }

        personsViewModel.uiState.test {
            assertEquals(PersonsUIState.Idle, awaitItem())
            assertEquals(PersonsUIState.Loading, awaitItem())
            assertEquals(PersonsUIState.People(samplePeople), awaitItem())
        }
    }


    @Test
    fun `test getting people throws an exception`() = runTest {

        val errorMessage = "This is a message"

        personsViewModel = PersonsViewModel(
            repository = MockFailureRepository(
                dispatcher = testsDispatcher, errorMessage = errorMessage
            )
        )

        assertThat(personsViewModel.uiState.value, `is`(PersonsUIState.Idle))

        collectJob = launch(UnconfinedTestDispatcher()) {
            personsViewModel.getPeople()
        }

        personsViewModel.uiState.test {
            assertEquals(PersonsUIState.Loading, awaitItem())
            assertEquals(PersonsUIState.Error(errorMessage), awaitItem())
        }
    }

    @After
    fun dispose() {
        Dispatchers.resetMain()
        if (::collectJob.isInitialized) {
            collectJob.cancel()
        }
    }
}