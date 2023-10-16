package ru.tanexc.notegraph.data.di

import android.content.Context
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.tanexc.notegraph.domain.interfaces.image.ImageHelper
import ru.tanexc.notegraph.presentation.util.image_loader.ImageLoader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

    @Singleton
    fun provideImageHelper(
        @ApplicationContext context: Context
    ): ImageHelper = ImageLoader(context)

}
