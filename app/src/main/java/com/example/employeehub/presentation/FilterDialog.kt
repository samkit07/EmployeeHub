package com.example.employeehub.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.employeehub.domain.EmployeeFilter

@Composable
fun FilterDialog(
    onDismissRequest: () -> Unit,
    onFilterSelected: (EmployeeFilter) -> Unit,
    employeeFilterM: MutableState<EmployeeFilter>,
) {
    val radioOptions = listOf(EmployeeFilter.ALL, EmployeeFilter.ACTIVE, EmployeeFilter.INACTIVE)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(employeeFilterM.value) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Filter") },
        text = {
            Column {
                radioOptions.forEach { employeeFilter ->
                    Row(
                        Modifier
                        .selectable(
                            selected = (employeeFilter == selectedOption),
                            onClick = {
                                onOptionSelected(employeeFilter)
                                onFilterSelected(employeeFilter)
                            }
                        ),
                        verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (employeeFilter == selectedOption),
                            onClick = {
                                onOptionSelected(employeeFilter)
                                onFilterSelected(employeeFilter)
                            }
                        )
                        Text(text = employeeFilter.name)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "OK")
            }
        }
    )
}