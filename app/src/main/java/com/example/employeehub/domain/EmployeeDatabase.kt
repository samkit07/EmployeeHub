package com.example.employeehub.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.employeehub.data.Employee

@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class EmployeeDatabase: RoomDatabase() {
    abstract fun employeeDao():EmployeeDao
}