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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.employeehub.data.Employee

@Composable
fun AddEmployeeScreen(
    viewModel: EmployeeViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val nameValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val phoneNumberValue = remember { mutableStateOf("") }
    val designationValue = remember { mutableStateOf("") }
    val departmentValue = remember { mutableStateOf("") }

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
            },
            label = { Text("Name") },
            isError = isNameError.value,
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next)
        )
        if (isNameError.value) {
            Text(text = "Name is required", color = Color.Red)
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
                phoneNumberValue.value = it
                isPhoneNumberError.value = false
            },
            label = { Text("Phone Number") },
            isError = isPhoneNumberError.value,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next)
            )
        if (isPhoneNumberError.value) {
            Text(text = "Invalid phone number format", color = Color.Red)
        }

        OutlinedTextField(
            value = designationValue.value,
            onValueChange = {
                designationValue.value = it
                isDesignationError.value = false
            },
            label = { Text("Designation") },
            isError = isDesignationError.value,
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next)
        )
        if (isDesignationError.value) {
            Text(text = "Designation is required", color = Color.Red)
        }

        OutlinedTextField(
            value = departmentValue.value,
            onValueChange = {
                departmentValue.value = it
                isDepartmentError.value = false
            },
            label = { Text("Department") },
            isError = isDepartmentError.value,
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done)
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
                    // Add employee to database
                    val employee = Employee(
                        name = nameValue.value,
                        email = emailValue.value,
                        phone = phoneNumberValue.value,
                        designation = designationValue.value,
                        department = departmentValue.value,
                        status = false
                    )
                    viewModel.addEmployee(employee)

                    // Navigate back to main screen
                    navigateBack()
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
                    if (designationValue.value.isEmpty()) {
                        isDesignationError.value = true
                    }
                    if (departmentValue.value.isEmpty()) {
                        isDepartmentError.value = true
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Add Employee")
        }
    }
}

fun validateInputs(
    name: String,
    email: String,
    phoneNumber: String,
    designation: String,
    department: String
): Boolean {
    // Check if required fields are not empty
    if (name.isEmpty() || designation.isEmpty() || department.isEmpty()) {
        return false
    }

    // Check if email has a valid format
    if (!email.isValidEmail()) {
        return false
    }

    // Check if phone number has a valid format
    if (!phoneNumber.isValidPhoneNumber()) {
        return false
    }

    // All inputs are valid
    return true
}

// Extension functions to check email and phone number formats
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPhoneNumber(): Boolean {
    return android.util.Patterns.PHONE.matcher(this).matches() && length == 10
}