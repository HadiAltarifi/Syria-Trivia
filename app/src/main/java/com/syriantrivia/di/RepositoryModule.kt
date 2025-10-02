package com.syriantrivia.di

import android.content.Context
import com.syriantrivia.data.local.database.GameSessionDao
import com.syriantrivia.data.local.database.QuestionDao
import com.syriantrivia.data.repository.GameSessionRepository
import com.syriantrivia.data.repository.QuestionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideQuestionRepository(
        @ApplicationContext context: Context,
        questionDao: QuestionDao
    ): QuestionRepository {
        return QuestionRepository(context, questionDao)
    }

    @Provides
    @Singleton
    fun provideGameSessionRepository(
        gameSessionDao: GameSessionDao
    ): GameSessionRepository {
        return GameSessionRepository(gameSessionDao)
    }
}
