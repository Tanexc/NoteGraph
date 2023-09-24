package ru.tanexc.notegraph.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tanexc.notegraph.data.repository.AuthRepositoryImpl
import ru.tanexc.notegraph.data.repository.NoteRepositoryImpl
import ru.tanexc.notegraph.data.repository.SettingsRepositoryImpl
import ru.tanexc.notegraph.domain.interfaces.repository.AuthRepository
import ru.tanexc.notegraph.domain.interfaces.repository.NoteRepository
import ru.tanexc.notegraph.domain.interfaces.repository.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun provideNoteRepository(impl: NoteRepositoryImpl): NoteRepository

    @Singleton
    @Binds
    abstract fun provideSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository


}