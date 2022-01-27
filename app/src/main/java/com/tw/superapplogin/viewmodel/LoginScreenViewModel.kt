package com.tw.superapplogin.viewmodel

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.okta.oidc.*
import com.okta.oidc.clients.web.WebAuthClient
import com.okta.oidc.storage.SharedPreferenceStorage
import com.okta.oidc.util.AuthorizationException
import com.tw.superapplogin.R
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
    val userName = MutableLiveData<String>()
    var config: OIDCConfig by Delegates.notNull()
    var state by mutableStateOf<LoginState>(LoginState.Initial)


    override fun onCleared() {
        super.onCleared()
        webAuthClient.unregisterCallback()
    }

    fun registerCallBack(context: Activity) //= viewModelScope.launch(Dispatchers.IO)
    {
        webAuthClient.registerCallback(object :
            ResultCallback<AuthorizationStatus, AuthorizationException> {
            override fun onCancel() {
                state = LoginState.Initial
            }
            override fun onError(msg: String?, exception: AuthorizationException?) {
                state = LoginState.Error(msg, exception)
            }
            override fun onSuccess(result: AuthorizationStatus) {
                when (result) {
                    AuthorizationStatus.AUTHORIZED -> {
                        state = LoginState.Success(result)
                    }
                    AuthorizationStatus.SIGNED_OUT -> {
                        state = LoginState.SignedOut(true)
                    }
                }
            }
        }, context)

    }

    fun signIn(activity: Activity) {
        config = OIDCConfig.Builder()
            .withJsonFile(activity, R.raw.config)
            .create()
        state = LoginState.Processing
        webAuthClient = Okta.WebAuthBuilder()
            .withConfig(config)
            .withContext(activity)
            .withStorage(SharedPreferenceStorage(activity, "web_client"))
            .setRequireHardwareBackedKeyStore(false)
            .create()
        registerCallBack(activity)
        webAuthClient.signIn(
            activity,
            AuthenticationPayload.Builder().setLoginHint(userName.value).build()
        )
    }


}

