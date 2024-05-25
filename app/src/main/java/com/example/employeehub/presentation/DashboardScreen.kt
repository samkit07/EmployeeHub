package com.example.employeehub.presentation

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.employeehub.data.Employee
import com.example.employeehub.R
import com.example.employeehub.domain.EmployeeFilter

@Composable
fun DashboardScreen(
    viewModel: EmployeeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val employees by viewModel.employees.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val openDialog = remember { mutableStateOf(false) }
    val employeeFilter = remember { mutableStateOf(EmployeeFilter.ALL) }

    LaunchedEffect(Unit) {
        viewModel.setAllEmployees()
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }) {
        Text(text = "Employees", style = MaterialTheme.typography.headlineLarge)

        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by name") },
                modifier = Modifier.weight(1f),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                })
            )

            if (openDialog.value){
                FilterDialog(onDismissRequest = { openDialog.value = false }, onFilterSelected = {
                    when(it){
                        EmployeeFilter.ALL -> viewModel.setAllEmployees()
                        EmployeeFilter.ACTIVE -> viewModel.filterActiveEmployee()
                        EmployeeFilter.INACTIVE -> viewModel.filterInactiveEmployee()
                    }
                    employeeFilter.value = it
                }, employeeFilter)
            }

            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_filter), contentDescription = "Edit")
            }
        }

        val filteredEmployees = employees.filter { it.name.contains(searchQuery, ignoreCase = true) }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(filteredEmployees) { employee ->
                EmployeeItem(
                    employee = employee,
                    onDeleteClick = { viewModel.deleteEmployee(employee) },
                    onEditClick = { navController.navigate("EditEmployeeScreen/${employee.id}") },
                    onStatusChange = { viewModel.updateEmployeeStatus(employee, it) }
                )
            }
        }
    }
}

@Composable
fun EmployeeItem(
    employee: Employee,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onStatusChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = employee.name, style = MaterialTheme.typography.titleLarge)
                Text(text = employee.email)
                Text(text = employee.phone)
                Text(text = employee.designation)
                Text(text = employee.department)
            }
            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                }
                IconButton(onClick = onEditClick) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
                }
                Switch(
                    checked = employee.status,
                    onCheckedChange = { onStatusChange(it) }
                )
            }
        }
    }
}