package com.reuben.sampletesting

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reuben.sampletesting.data.MockSuccessPeopleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.robolectric.annotation.Config
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
class PersonsViewModelTests {

    private lateinit var personsViewModel: PersonsViewModel

    private val testsDispatcher = StandardTestDispatcher()



    @Test
    fun `test getting people is successful`() = runTest {
        personsViewModel = PersonsViewModel(
            repository = MockSuccessPeopleRepository(
                testsDispatcher
            )
        )

        assertThat(personsViewModel.uiState.value, `is`(PersonsUIState.Idle))
    }

    @After
    fun tearDown() {

    }
}