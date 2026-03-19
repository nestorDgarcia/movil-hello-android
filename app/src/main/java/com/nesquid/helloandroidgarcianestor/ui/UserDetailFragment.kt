package com.nesquid.helloandroidgarcianestor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nesquid.helloandroidgarcianestor.databinding.FragmentUserDetailBinding
import com.nesquid.helloandroidgarcianestor.viewmodel.UserViewModel

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.selectedUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.textViewName.text = "Nombre: ${it.name}"
                binding.textViewEmail.text = "Email: ${it.email}"
                binding.textViewAge.text = "Edad: ${it.age} años"
                binding.textViewId.text = "ID: ${it.id}"
            }
        }
    }

    private fun setupClickListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
