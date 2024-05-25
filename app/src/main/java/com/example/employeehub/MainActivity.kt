package com.example.employeehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.employeehub.presentation.AddEmployeeScreen
import com.example.employeehub.presentation.DashboardScreen
import com.example.employeehub.presentation.EditEmployeeScreen
import com.example.employeehub.presentation.EmployeeViewModel
import com.example.employeehub.presentation.MainScreen
import com.example.employeehub.ui.theme.EmployeeHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: EmployeeViewModel by viewModels<EmployeeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmployeeHubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "MainScreen") {
                        composable("MainScreen") {
                            MainScreen(viewModel = viewModel, navController = navController)
                        }
                        composable("AddEmployeeScreen") {
                            AddEmployeeScreen(viewModel = viewModel, navigateBack = {
                                navController.popBackStack()
                            })
                        }
                        composable("DashboardScreen") {
                            DashboardScreen(viewModel = viewModel, navController = navController)
                        }
                        composable("EditEmployeeScreen/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?:"0"
                            EditEmployeeScreen(viewModel = viewModel, navController = navController, employeeId = id)
                        }
                    }
                }
            }
        }
    }
}