package com.yourssohail.jetpackworkmanager

import com.yourssohail.jetpackworkmanager.data.IWorkerRepository
import com.yourssohail.jetpackworkmanager.data.WorkerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WorkerModule {

    @Singleton
    @Provides
    fun getRepository(): IWorkerRepository {
        return WorkerRepository()
    }
}