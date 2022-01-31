package com.tw.superapplogin.ui.composables

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tw.superapplogin.ui.views.Home
import com.tw.superapplogin.viewmodel.LoginScreenViewModel


@Composable
fun LoginScreen(activity: Activity, viewModel: LoginScreenViewModel) {
    val context = LocalContext.current

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = { Text(text = "SuperApp Login") })
            },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                val state = viewModel.uiState.collectAsState().value
                when ( state) {
                    is LoginScreenViewModel.LoginState.Initial -> {
                        GetLoginControls(viewModel, activity)
                    }
                    is LoginScreenViewModel.LoginState.Success -> {
                        /*val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)*/
                        Home(activity,viewModel)
                    }
                    is LoginScreenViewModel.LoginState.Error -> {
                        Toast.makeText(
                            context,
                            state.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is LoginScreenViewModel.LoginState.Processing -> {
                        CircularProgressIndicator()
                    }
                    is LoginScreenViewModel.LoginState.SignedOut -> {
                        GetLoginControls(viewModel, activity)
                    }
                }
            }
        }

    }
}

@Composable
private fun GetLoginControls(
    viewModel: LoginScreenViewModel,
    activity: Activity
) {
    Button(onClick = {
        viewModel.signIn(activity)
    }, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Sign in")
    }
}




