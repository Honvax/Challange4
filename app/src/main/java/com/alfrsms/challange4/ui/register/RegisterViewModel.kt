package com.alfrsms.challange4.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfrsms.challange4.data.LocalRepository
import com.alfrsms.challange4.data.local.user.UserEntity
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: LocalRepository) : ViewModel() {

    fun registerUser(user: UserEntity) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }
}