package com.example.bmiNewAge.di

import com.example.bmiNewAge.data.repository.ApplicationRepositoryImpl
import com.example.bmiNewAge.domain.repository.ApplicationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun getApplicationRepository() : ApplicationRepository {
        return ApplicationRepositoryImpl()
    }

}