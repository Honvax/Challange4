package com.alfrsms.challange4.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfrsms.challange4.data.LocalRepository
import com.alfrsms.challange4.data.local.user.UserEntity
import com.alfrsms.challange4.wrapper.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LocalRepository) : ViewModel(){

    private var _getUserResult = MutableLiveData<Resource<UserEntity>>()
    val getUser: LiveData<Resource<UserEntity>> get() = _getUserResult

    fun getUser(username: String) {
        viewModelScope.launch {
            _getUserResult.postValue(repository.getUser(username))
        }
    }
}