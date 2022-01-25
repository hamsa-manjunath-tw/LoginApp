package com.tw.superapplogin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.okta.authn.sdk.client.AuthenticationClient
import com.okta.oidc.AuthorizationStatus
import com.okta.oidc.OIDCConfig
import com.okta.oidc.Okta
import com.okta.oidc.ResultCallback
import com.okta.oidc.clients.AuthClient
import com.okta.oidc.clients.web.WebAuthClient
import com.okta.oidc.util.AuthorizationException
import com.tw.superapplogin.ui.composables.LoginScreen
import com.tw.superapplogin.ui.theme.SuperAppLoginTheme
import kotlin.properties.Delegates

private var callbackManager = ActivityResultCallbackManager()

class MainActivity : ComponentActivity() {

    lateinit var webAuthClient: WebAuthClient
    lateinit var authClient: AuthClient
    lateinit var authenticationClient: AuthenticationClient

    var config: OIDCConfig by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SuperAppLoginTheme {
                //LoginScreen()
                CompositionLocalProvider(
                    LocalActivityResultCallbackManager provides callbackManager
                ) {
                    LoginScreen(this)
                }
               /* Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
                ) {
                    Button(onClick = {
                             createWebClient()

                    }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Sign in")
                    }
                }*/
            }


        }
        config = OIDCConfig.Builder()
            .withJsonFile(this, R.raw.config)
            .create()
    }



    fun createWebClient() {
        println("Called webclient")
        webAuthClient = Okta.WebAuthBuilder()
            .withConfig(config)
            .withContext(this)
            //.withStorage(SharedPreferenceStorage(context, PREF_STORAGE_WEB))
            .setRequireHardwareBackedKeyStore(false)
            .create()

        webAuthClient.registerCallback(object :
            ResultCallback<AuthorizationStatus, AuthorizationException> {
            override fun onCancel() {
                 //network_progress.hide()
                 //showMessage("cancel")
                println("On cancel method")
            }

            override fun onError(msg: String?, exception: AuthorizationException?) {
                //signInError(msg, exception)
                println("On error method")
            }

            override fun onSuccess(result: AuthorizationStatus) {
                //network_progress.hide()
                when (result) {
                    AuthorizationStatus.AUTHORIZED ->   println("On authorized status")
                    AuthorizationStatus.SIGNED_OUT ->   println("On signed out ")
                }
            }
        },this)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (!callbackManager.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

/*

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SuperAppLoginTheme {
        LoginScreen(this)
    }
}*/
