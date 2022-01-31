package com.tw.superapplogin.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.okta.oidc.*
import com.okta.oidc.clients.web.WebAuthClient
import com.okta.oidc.storage.SharedPreferenceStorage
import com.okta.oidc.util.AuthorizationException
import com.tw.superapplogin.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.properties.Delegates


class LoginScreenViewModel : ViewModel() {
    sealed class LoginState {
        object Initial : LoginState()
        object Processing : LoginState()
        data class Success(val authStatus: AuthorizationStatus) : LoginState()
        data class Error(val msg: String?, val exception: AuthorizationException?) : LoginState()
        data class SignedOut(val isSignedOut: Boolean) : LoginState()
    }

    lateinit var webAuthClient: WebAuthClient
    var config: OIDCConfig by Delegates.notNull()
    private val _uiState = MutableStateFlow<LoginState>(LoginState.Initial)
    val uiState: StateFlow<LoginState> = _uiState


    fun registerCallBack(context: Activity) //= viewModelScope.launch(Dispatchers.IO)
    {
        webAuthClient.registerCallback(object :
            ResultCallback<AuthorizationStatus, AuthorizationException> {
            override fun onCancel() {
                _uiState.value = LoginState.Initial
            }
            override fun onError(msg: String?, exception: AuthorizationException?) {
                _uiState.value = LoginState.Error(msg, exception)
            }
            override fun onSuccess(result: AuthorizationStatus) {
                when (result) {
                    AuthorizationStatus.AUTHORIZED -> {
                        _uiState.value = LoginState.Success(result)
                    }
                    AuthorizationStatus.SIGNED_OUT -> {
                        _uiState.value = LoginState.SignedOut(true)
                    }
                }
            }
        }, context)

    }

    fun signIn(activity: Activity) {
        config = OIDCConfig.Builder()
            .withJsonFile(activity, R.raw.config)
            .create()
        _uiState.value = LoginState.Processing
        webAuthClient = Okta.WebAuthBuilder()
            .withConfig(config)
            .withContext(activity)
            .withStorage(SharedPreferenceStorage(activity, "web_client"))
            //.withCallbackExecutor(Executors.newSingleThreadExecutor())
            .setRequireHardwareBackedKeyStore(false)
            .create()
        registerCallBack(activity)
        webAuthClient.signIn(
            activity,
            null
        )

        //AuthenticationPayload.Builder().setLoginHint("").build()
    }

    fun signOut(activity: Activity)
    {
        webAuthClient.signOutOfOkta(activity)
    }
}

