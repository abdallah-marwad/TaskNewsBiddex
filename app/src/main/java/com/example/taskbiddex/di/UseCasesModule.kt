package com.example.taskbiddex.di

import com.example.taskbiddex.features.news.data.repoImpl.NewsRepoImpl
import com.example.taskbiddex.features.news.domain.usecase.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun newsUseCase(repo: NewsRepoImpl) = GetNewsUseCase(repo)
}

