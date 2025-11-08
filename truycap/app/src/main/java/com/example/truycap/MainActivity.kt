package com.example.truycap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.truycap.ui.permission.PermissionScreen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "permission") {
                    composable("permission") {
                        PermissionScreen(
                            onFinish = {
                                navController.navigate("login") {
                                    popUpTo("permission") { inclusive = true }
                                }
                            }
                        )
                    }
                    }
                }
            }
        }
    }