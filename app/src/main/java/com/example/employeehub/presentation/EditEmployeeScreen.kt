package com.example.employeehub.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.employeehub.data.Employee

@Composable
fun EditEmployeeScreen(
    viewModel: EmployeeViewModel = hiltViewModel(),
    navController: NavController,
    employeeId: String
) {
    val employee = viewModel.employees.collectAsState().value.find { it.id == employeeId.toInt() }

    val nameValue = remember { mutableStateOf(employee?.name ?: "") }
    val emailValue = remember { mutableStateOf(employee?.email ?: "") }
    val phoneNumberValue = remember { mutableStateOf(employee?.phone ?: "") }
    val designationValue = remember { mutableStateOf(employee?.designation ?: "") }
    val departmentValue = remember { mutableStateOf(employee?.department ?: "") }

    val isNameError = remember { mutableStateOf(false) }
    val isEmailError = remember { mutableStateOf(false) }
    val isPhoneNumberError = remember { mutableStateOf(false) }
    val isDesignationError = remember { mutableStateOf(false) }
    val isDepartmentError = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nameValue.value,
            onValueChange = {
                nameValue.value = it
                isNameError.value = false
                if (!isValidCharacters(it)) {
                    isNameError.value = true
                }
            },
            label = { Text("Name") },
            isError = isNameError.value,
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next)
        )
        if (isNameError.value) {
            Text(text = "Valid Name is required", color = Color.Red)
        }

        OutlinedTextField(
            value = emailValue.value,
            onValueChange = {
                emailValue.value = it
                isEmailError.value = false
            },
            label = { Text("Email") },
            isError = isEmailError.value,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if (isEmailError.value) {
            Text(text = "Invalid email format", color = Color.Red)
        }

        OutlinedTextField(
            value = phoneNumberValue.value,
            onValueChange = {
                if (it.length <= 10 && it[0] in listOf('6', '7', '8', '9')){
                    phoneNumberValue.value = it
                    isPhoneNumberError.value = false
                    if (!it.isValidPhoneNumber()) {
                        isPhoneNumberError.value = true
                    }
                }
            },
            label = { Text("Phone Number") },
            isError = isPhoneNumberError.value,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next)
        )
        if (isPhoneNumberError.value) {
            Text(text = "Invalid phone number format", color = Color.Red)
        }

        val designationList = listOf("Select Designation", "FrontEnd Developer", "Backend Developer", "iOS Developer", "Android Developer")


        SampleSpinner(
            designationValue,
            "Designation",
            designationList,
            preselected = designationValue.value,
            onSelectionChanged = { selected -> /* do something with selected */ },
            isDesignationError
        )

        if (isDesignationError.value) {
            Text(text = "Designation is required", color = Color.Red)
        }

        val departmentList = listOf("Select Department", "Android", "iOS", "Web")

        SampleSpinner(
            departmentValue,
            "Department",
            departmentList,
            preselected = departmentValue.value,
            onSelectionChanged = { selected -> /* do something with selected */ },
            isDepartmentError
        )

        if (isDepartmentError.value) {
            Text(text = "Department is required", color = Color.Red)
        }

        Button(
            onClick = {
                // Validate inputs
                val isValid = validateInputs(
                    nameValue.value,
                    emailValue.value,
                    phoneNumberValue.value,
                    designationValue.value,
                    departmentValue.value
                )

                if (isValid) {
                    val updatedEmployee = employee?.copy(
                        name = nameValue.value,
                        email = emailValue.value,
                        phone = phoneNumberValue.value,
                        designation = designationValue.value,
                        department = departmentValue.value
                    )
                    if (updatedEmployee != null) {
                        viewModel.editEmployee(updatedEmployee)
                        navController.popBackStack()
                    }
                } else {
                    // Show error messages for invalid inputs
                    if (nameValue.value.isEmpty()) {
                        isNameError.value = true
                    }
                    if (!emailValue.value.isValidEmail()) {
                        isEmailError.value = true
                    }
                    if (!phoneNumberValue.value.isValidPhoneNumber()) {
                        isPhoneNumberError.value = true
                    }
                    if (designationValue.value.isEmpty() || designationValue.value.contains("Select Designation")) {
                        isDesignationError.value = true
                    }
                    if (departmentValue.value.isEmpty() || departmentValue.value.contains("Select Department")) {
                        isDepartmentError.value = true
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Update Employee")
        }
    }
}