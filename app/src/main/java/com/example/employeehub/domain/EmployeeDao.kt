package com.example.employeehub.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.employeehub.data.Employee
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("update employees set status = :status where id = :id")
    suspend fun updateEmployeeStatus(id: Int, status:Boolean)

    @Query("select * from employees")
    fun getAllEmployees(): Flow<List<Employee>>

    @Query("select * from employees where status = 1")
    fun getAllActiveEmployees(): Flow<List<Employee>>

    @Query("select * from employees where status = 0")
    fun getAllInactiveEmployees(): Flow<List<Employee>>
}