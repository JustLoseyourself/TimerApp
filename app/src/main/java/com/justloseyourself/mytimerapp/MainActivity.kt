package com.justloseyourself.mytimerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loseyourself.mytimerapp.ui.screens.TimersScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerApp()
        }
    }
}

@Composable
fun TimerApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_page") {
        composable("main_page") {
            TimersScreen()
        }
    }
}