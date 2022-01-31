package com.tw.superapplogin

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tw.superapplogin.ui.composables.LoginScreen
import com.tw.superapplogin.ui.theme.SuperAppLoginTheme
import com.tw.superapplogin.viewmodel.LoginScreenViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: LoginScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SuperAppLoginTheme {
                LoginScreen(this, viewModel)

            }

        }
    }
   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        //must pass the results back to the WebAuthClient.
        viewModel.webAuthClient.handleActivityResult(requestCode, resultCode, data)
    }*/

}


