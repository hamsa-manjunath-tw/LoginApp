package com.tw.superapplogin

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.okta.oidc.*
import com.okta.oidc.clients.web.WebAuthClient
import com.okta.oidc.util.AuthorizationException
import kotlin.properties.Delegates

class LoginScreenViewModel : ViewModel(), ActivityResultCallbackI {
    sealed class LoginState {
        object Initial: LoginState()
        object Processing: LoginState()
        data class Success(val authStatus: AuthorizationStatus): LoginState()
        data class Error(val msg: String?,val exception: AuthorizationException?): LoginState()
        data class SignedOut(val isSignedOut:Boolean): LoginState()
    }
    lateinit var webAuthClient: WebAuthClient
    val userName = MutableLiveData<String>()
    var config: OIDCConfig by Delegates.notNull()
    var state by mutableStateOf<LoginState>(LoginState.Initial)
        private set

    override fun onCleared() {
        super.onCleared()
        webAuthClient.unregisterCallback()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
       // println("inside on activity result")
        //super.onActivityResult(requestCode, resultCode, data)
        return false;
    }

    fun login(context: Activity) {
        config = OIDCConfig.Builder()
            .withJsonFile(context, R.raw.config)
            .create()
        state = LoginState.Processing
        webAuthClient = Okta.WebAuthBuilder()
            .withConfig(config)
            .withContext(context)
            //.withStorage(SharedPreferenceStorage(context, PREF_STORAGE_WEB))
            .setRequireHardwareBackedKeyStore(false)
            .create()

        webAuthClient.registerCallback(object :
            ResultCallback<AuthorizationStatus, AuthorizationException> {
            override fun onCancel() {
                //network_progress.hide()
                //showMessage("cancel")
                println("On cancel method")
                state = LoginState.Initial
            }

            override fun onError(msg: String?, exception: AuthorizationException?) {
                //signInError(msg, exception)
                println("On error method")
                state = LoginState.Error(msg,exception)
            }

            override fun onSuccess(result: AuthorizationStatus) {
                //network_progress.hide()
                when (result) {
                    AuthorizationStatus.AUTHORIZED ->   {
                        state = LoginState.Success(result)
                        println("On authrized status")}
                    AuthorizationStatus.SIGNED_OUT ->  {
                        println("On signed out ")
                        state= LoginState.SignedOut(true)}
                }
            }
        }
        , context)

        webAuthClient.signIn(context, AuthenticationPayload.Builder().setLoginHint("pisedi2112@peykesabz.com").build())

    }



    }

