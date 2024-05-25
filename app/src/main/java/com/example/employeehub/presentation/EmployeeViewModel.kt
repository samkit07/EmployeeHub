package com.example.employeehub.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeehub.data.Employee
import com.example.employeehub.domain.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(private val employeeRepository: EmployeeRepository): ViewModel() {

    private val _employees = MutableStateFlow<List<Employee>>(emptyList())
    val employees: StateFlow<List<Employee>> = _employees.asStateFlow()

    init {
        setAllEmployees()
    }

    fun setAllEmployees(){
        viewModelScope.launch {
            _employees.value = employeeRepository.getAllEmployee().first()
        }
    }

    fun addEmployee(employee: Employee){
        viewModelScope.launch {
            employeeRepository.insertEmployee(employee)
            _employees.value = employeeRepository.getAllEmployee().first()
        }
    }

    fun editEmployee(employee: Employee){
        viewModelScope.launch {
            employeeRepository.updateEmployee(employee)
            _employees.value = employeeRepository.getAllEmployee().first()
        }
    }

    fun deleteEmployee(employee: Employee){
        viewModelScope.launch {
            employeeRepository.deleteEmployee(employee)
            _employees.value = employeeRepository.getAllEmployee().first()
        }
    }

    fun updateEmployeeStatus(employee: Employee, status: Boolean){
        viewModelScope.launch {
            employeeRepository.updateEmployeeStatus(employee.id, status)
            _employees.value = employeeRepository.getAllEmployee().first()
        }
    }

    fun filterActiveEmployee(){
        viewModelScope.launch {
            _employees.value = employeeRepository.getActiveEmployee().first()
        }
    }

    fun filterInactiveEmployee(){
        viewModelScope.launch {
            _employees.value = employeeRepository.getInactiveEmployee().first()
        }
    }
}