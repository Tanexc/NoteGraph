package ru.tanexc.notegraph.presentation.screen.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _login: MutableState<String> = mutableStateOf("")
    val login: String by _login

    private val _password: MutableState<String> = mutableStateOf("")
    val password: String by _password

    fun updateLogin(value: String) {
        _login.value = value
    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.authByEmail(login, password)
        }
    }


}