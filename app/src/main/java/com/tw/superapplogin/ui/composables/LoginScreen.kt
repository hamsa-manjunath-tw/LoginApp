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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tw.superapplogin.HomeActivity
import com.tw.superapplogin.viewmodel.LoginScreenViewModel


@Composable
fun LoginScreen(activity: Activity) {
    val context = LocalContext.current
    val viewModel: LoginScreenViewModel =
        viewModel()


    Surface(color = MaterialTheme.colors.background) {
        var textFieldState by remember {
            mutableStateOf("")
        }
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

                when (val state = viewModel.uiState.collectAsState().value) {
                    is LoginScreenViewModel.LoginState.Initial -> {
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
                            viewModel.signIn(activity)

                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Sign in")
                        }

                    }
                    is LoginScreenViewModel.LoginState.Success -> {
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)
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
                }
            }
        }

    }
}




