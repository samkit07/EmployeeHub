package com.example.employeehub.di

import android.content.Context
import androidx.room.Room
import com.example.employeehub.domain.EmployeeDao
import com.example.employeehub.domain.EmployeeDatabase
import com.example.employeehub.domain.EmployeeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEmployeeDatabase(@ApplicationContext context: Context):EmployeeDatabase{
        return Room.databaseBuilder(
            context,
            EmployeeDatabase::class.java,
            "employee_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideEmployeeDao(db: EmployeeDatabase) = db.employeeDao()

    @Provides
    @Singleton
    fun provideEmployeeRepository(dao: EmployeeDao): EmployeeRepository = EmployeeRepository(dao)
}