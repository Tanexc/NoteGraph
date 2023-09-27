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
import ru.tanexc.notegraph.domain.interfaces.use_cases.user.AuthAsGuestUseCase
import ru.tanexc.notegraph.domain.interfaces.use_cases.user.AuthByEmailUseCase
import ru.tanexc.notegraph.domain.interfaces.use_cases.user.SignUpUserUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authAsGuestUseCase: AuthAsGuestUseCase,
    private val authByEmailUseCase: AuthByEmailUseCase,
    private val signUpUserUseCase: SignUpUserUseCase
): ViewModel() {

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authByEmailUseCase(email, password)
        }
    }

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUserUseCase(email, password, name)
        }
    }

    fun signInAsGuest() {
        viewModelScope.launch(Dispatchers.IO) {
            authAsGuestUseCase()
        }
    }



}