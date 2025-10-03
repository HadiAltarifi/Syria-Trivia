package com.syriantrivia.di

import android.content.Context
import androidx.room.Room
import com.syriantrivia.data.local.database.GameSessionDao
import com.syriantrivia.data.local.database.QuestionDao
import com.syriantrivia.data.local.database.SyriaTriviaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SyriaTriviaDatabase {
        return Room.databaseBuilder(
            context,
            SyriaTriviaDatabase::class.java,
            "syria_trivia_database"
        )
        .fallbackToDestructiveMigration() // Delete and recreate DB on version change
        .build()
    }

    @Provides
    @Singleton
    fun provideQuestionDao(database: SyriaTriviaDatabase): QuestionDao {
        return database.questionDao()
    }

    @Provides
    @Singleton
    fun provideGameSessionDao(database: SyriaTriviaDatabase): GameSessionDao {
        return database.gameSessionDao()
    }
}
