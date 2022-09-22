package com.reuben.sampletesting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.reuben.sampletesting.data.PeopleApiImpl
import com.reuben.sampletesting.data.PeopleRepositoryImpl
import com.reuben.sampletesting.databinding.FragmentSecondBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModelFactory =
        PersonsViewModel.PersonsFactory(PeopleRepositoryImpl(api = PeopleApiImpl()))




    private val personsViewModel: PersonsViewModel by viewModels { viewModelFactory }

    private val peopleAdapter by lazy {
        PeopleAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewmodel()
    }

    private fun observeViewmodel() {
        personsViewModel.getPeople()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                personsViewModel.uiState.collect { uistate ->
                    when (uistate) {
                        is PersonsUIState.Error -> {

                        }
                        PersonsUIState.Idle -> {

                        }
                        is PersonsUIState.People -> {
                            binding.recyclerPeople.adapter = peopleAdapter
                            peopleAdapter.submitList(uistate.data)
                        }
                        PersonsUIState.Loading -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}