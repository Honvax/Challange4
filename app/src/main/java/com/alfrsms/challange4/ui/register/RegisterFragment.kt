package com.alfrsms.challange4.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alfrsms.challange4.R
import com.alfrsms.challange4.data.local.user.UserEntity
import com.alfrsms.challange4.databinding.FragmentRegisterBinding
import com.alfrsms.challange4.di.ServiceLocator
import com.alfrsms.challange4.utils.viewModelFactory


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModelFactory {
        RegisterViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        if (validateInput()) {
            val user = UserEntity(
                username = binding.etUsername.text.toString(),
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
            viewModel.registerUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = "Username or password must not be empty"
        }
        if (email.isEmpty()) {
            isValid = false
            binding.etEmail.error = "Email must not be empty"
        }
        if (password.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Password must not be empty", Toast.LENGTH_SHORT).show()
        }
        if (confirmPassword.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Confirm password must not be empty", Toast.LENGTH_SHORT).show()
        }
        if (password != confirmPassword) {
            isValid = false
            Toast.makeText(requireContext(), "Password does not match!", Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}