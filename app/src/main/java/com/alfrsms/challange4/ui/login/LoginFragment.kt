package com.alfrsms.challange4.ui.login
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.alfrsms.challange4.R
import com.alfrsms.challange4.data.local.user.UserEntity
import com.alfrsms.challange4.databinding.FragmentLoginBinding
import com.alfrsms.challange4.di.ServiceLocator
import com.alfrsms.challange4.utils.viewModelFactory
import com.alfrsms.challange4.wrapper.Resource


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModelFactory {
        LoginViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(LOGIN_SHARED_PREF,
            Context.MODE_PRIVATE
        )

        binding.btnLogin.setOnClickListener { checkLogin() }

        if (isLoginInfoValid()) {
            navigateToHome()
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun checkLogin() {
        if (validateInput()){
            val username = binding.etUsername.text.toString()
            viewModel.getUser(username)

            viewModel.getUser.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> checkUser(it.payload)
                    is Resource.Error -> Toast.makeText(requireContext(), "Error while getting data", Toast.LENGTH_SHORT).show()
                    else -> {}
                }
            }

        }
    }

    private fun checkUser(user: UserEntity?) {
        user?.let {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val userLoggedIn = username == user.username && password == user.password
            if (userLoggedIn) {
                navigateToHome()
                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_SHORT).show()
            }
            saveLoginInfo(userLoggedIn)
        }
    }

    private fun saveLoginInfo(loginInfo: Boolean){
        sharedPreferences.edit {
            putBoolean(LOGGED_IN_KEY, loginInfo)
        }
    }

    private fun isLoginInfoValid(): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN_KEY, false)
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = "Username must not be empty"
        }
        if (password.isEmpty()) {
            isValid = false
            Toast.makeText(requireContext(), "Password must not be empty", Toast.LENGTH_SHORT).show()        }
        return isValid
    }

    private fun navigateToHome() {
        val option = NavOptions.Builder()
            .setPopUpTo(R.id.loginFragment, true)
            .build()
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment, null, option)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val LOGIN_SHARED_PREF = "login_shared_pref"
        const val LOGGED_IN_KEY = "logged_in"
    }
}