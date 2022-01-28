package com.tw.superapplogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tw.superapplogin.ui.composables.LoginScreen
import com.tw.superapplogin.ui.theme.SuperAppLoginTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            SuperAppLoginTheme {
                LoginScreen(this)
            }

        }
    }
}


