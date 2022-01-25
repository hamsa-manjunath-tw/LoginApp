package com.tw.superapplogin.ui.composables
import android.app.Activity
import android.location.GnssAntennaInfo
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.okta.authn.sdk.client.AuthenticationClient
import com.okta.oidc.AuthorizationStatus
import com.okta.oidc.OIDCConfig
import com.okta.oidc.Okta
import com.okta.oidc.ResultCallback
import com.okta.oidc.clients.AuthClient
import com.okta.oidc.clients.web.WebAuthClient
import com.okta.oidc.storage.SharedPreferenceStorage
import com.okta.oidc.util.AuthorizationException
import com.tw.superapplogin.LocalActivityResultCallbackManager
import com.tw.superapplogin.LoginScreenViewModel
import com.tw.superapplogin.MainActivity
import kotlin.properties.Delegates


@Composable
fun LoginScreen(activity: Activity){

    val viewModel: LoginScreenViewModel =
        viewModel()

    val callbackManager = LocalActivityResultCallbackManager.current
    DisposableEffect(Unit) {
        callbackManager.addListener(viewModel)
        onDispose {
            callbackManager.removeListener(viewModel)
        }
    }


    //val context = LocalContext.current
    Surface(color = MaterialTheme.colors.background) {
        val scaffoldState = rememberScaffoldState()
        var textFieldState by remember {
            mutableStateOf("")
        }
        val scope = rememberCoroutineScope()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = { Text(text = "SuperApp Login") })
            },
            scaffoldState = scaffoldState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                TextField(value = textFieldState, label = {
                    Text("User hint (optional)")
                }, onValueChange = {
                    textFieldState = it
                },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    viewModel.userName.value = textFieldState

                    viewModel.login(activity)
                   // when(viewModel.state== LoginScreenViewModel.LoginState.Success)
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Sign in")
                }


            }

        }


    }
}




