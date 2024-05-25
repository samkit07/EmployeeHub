package com.example.employeehub.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MainScreen(
    viewModel: EmployeeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val employees = viewModel.employees.collectAsState(initial = emptyList())
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(route = "AddEmployeeScreen") }) {
            Text(text = "Add Employee")
        }
        Button(onClick = { navController.navigate(route = "DashboardScreen") }) {
            Text(text = "Dashboard")
        }
    }
}