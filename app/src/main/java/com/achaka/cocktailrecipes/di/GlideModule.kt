package com.achaka.cocktailrecipes.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GlideModule {
    
    @Provides
    @Singleton
    fun provideGlideInstance(app: Application): RequestManager = Glide.with(app)

}