package com.tw.superapplogin.ui.views

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tw.superapplogin.ui.theme.SuperAppLoginTheme
import com.tw.superapplogin.viewmodel.LoginScreenViewModel

/*class HomeActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(this.applicationContext)
        println("Base Context >>>"+baseContext.applicationContext)

        setContent {
            SuperAppLoginTheme {
                Home()
            }
        }
    }
}*/

@Composable
fun Home(activity: Activity, viewModel: LoginScreenViewModel) {

    Surface(color = MaterialTheme.colors.background) {
      /*  Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = { Text(text = "SuperApp Login") },
                    *//*actions = {
                        Button(onClick = { viewModel.signOut(activity) }) {
                            Text("Sign Out")
                        }
                    }*//*
                )

            },

            ) {*/
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Button(onClick = { viewModel.signOut(activity) }) {
                    Text("Sign Out")
                }

            }
        //}
    }

}


