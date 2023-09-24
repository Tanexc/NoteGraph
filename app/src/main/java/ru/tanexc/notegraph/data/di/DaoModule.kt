package ru.tanexc.notegraph.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tanexc.notegraph.data.firebase.dao.NoteDaoImpl
import ru.tanexc.notegraph.data.firebase.dao.UserDaoImpl
import ru.tanexc.notegraph.domain.interfaces.dao.NoteDao
import ru.tanexc.notegraph.domain.interfaces.dao.UserDao

@Module
@InstallIn(SingletonComponent::class)
abstract class DaoModule {

    @Binds
    abstract fun provideUserDao(impl: UserDaoImpl): UserDao

    @Binds
    abstract fun provideNoteDao(impl: NoteDaoImpl): NoteDao
}
