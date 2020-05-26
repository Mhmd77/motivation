package com.tehran.motivation.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.tehran.motivation.Event
import com.tehran.motivation.MyApplication
import com.tehran.motivation.R
import com.tehran.motivation.data.LoginData
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.SignUpData
import com.tehran.motivation.data.source.remote.AuthApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _signupHappened = MutableLiveData<Event<Any>>()
    val signupHappened: LiveData<Event<Any>> = _signupHappened

    private val _loginHappened = MutableLiveData<Event<Any>>()
    val loginHappened: LiveData<Event<Any>> = _loginHappened

    private val _showMainActivity = MutableLiveData<Event<Any>>()
    val showMainActivity: LiveData<Event<Any>> = _showMainActivity

    private val _snackMessage = MutableLiveData<Event<String>>()
    val snackMessage: LiveData<Event<String>> = _snackMessage

    private val _authResultSignup = MutableLiveData<Result<String>>(Result.Init)
    val authResultSignup: LiveData<Result<String>> = _authResultSignup

    private val _authResultLogin = MutableLiveData<Result<String>>(Result.Init)
    val authResultLogin: LiveData<Result<String>> = _authResultLogin

    val progressSignup = Transformations.map(authResultSignup) {
        Event(it !is Result.Loading)
    }

    val progressLogin = Transformations.map(authResultLogin) {
        Event(it !is Result.Loading)
    }

    fun signupButton() {
        _signupHappened.value = Event(Any())
        _authResultSignup.value = Result.Loading
    }

    fun loginButton() {
        _loginHappened.value = Event(Any())
        _authResultLogin.value = Result.Loading

    }

    fun signUp(data: SignUpData) = scope.launch {
        val signupDeferred = AuthApi.api.signUpAsync(data)
        try {
            val response = signupDeferred.await()
            _authResultSignup.value = Result.Success(response.message)
            _snackMessage.postValue(Event(response.message))
        } catch (e: Exception) {
            _snackMessage.value =
                Event(getApplication<MyApplication>().getString(R.string.failed_connection))
            _authResultSignup.value = Result.Error(e)
        }
    }

    fun login(data: LoginData) = scope.launch {
        val loginDeferred = AuthApi.api.loginAsync(data)
        try {
            val response = loginDeferred.await()
            _authResultLogin.value = Result.Success(response.message)
            _snackMessage.postValue(Event(response.message))
            if (response.status == 200) {
                Timber.d("message: ${response.message}")
                _showMainActivity.value = Event(Any())
                getApplication<MyApplication>().setupRecurringWork()
            } else {
                Timber.d("code: ${response.status}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _snackMessage.value =
                Event(getApplication<MyApplication>().getString(R.string.failed_connection))
            _authResultLogin.value = Result.Error(e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}