package com.example.employeehub.domain

import com.example.employeehub.data.Employee
import javax.inject.Inject

class EmployeeRepository @Inject constructor(private val employeeDao: EmployeeDao) {
    suspend fun insertEmployee(employee: Employee) = employeeDao.insertEmployee(employee)

    suspend fun updateEmployee(employee: Employee) = employeeDao.updateEmployee(employee)

    suspend fun deleteEmployee(employee: Employee) = employeeDao.deleteEmployee(employee)

    suspend fun updateEmployeeStatus(id: Int, status: Boolean) = employeeDao.updateEmployeeStatus(id, status)

    fun getAllEmployee() = employeeDao.getAllEmployees()

    fun getActiveEmployee() = employeeDao.getAllActiveEmployees()

    fun getInactiveEmployee() = employeeDao.getAllInactiveEmployees()

}