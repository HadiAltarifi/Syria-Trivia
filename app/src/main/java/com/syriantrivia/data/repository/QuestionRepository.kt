package com.syriantrivia.data.repository

import android.content.Context
import com.syriantrivia.data.local.assets.JsonParser
import com.syriantrivia.data.local.database.QuestionDao
import com.syriantrivia.data.model.Difficulty
import com.syriantrivia.data.model.ModuleType
import com.syriantrivia.data.model.Question
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val questionDao: QuestionDao
) {
    private var isInitialized = false

    suspend fun initializeDatabase() {
        withContext(Dispatchers.IO) {
            if (!isInitialized && questionDao.getQuestionCount() == 0) {
                val jsonParser = JsonParser(context)
                val questions = jsonParser.parseQuestions()
                questionDao.insertAll(questions)
                isInitialized = true
            }
        }
    }

    suspend fun getQuestionsByModuleAndDifficulty(
        moduleType: ModuleType,
        difficulty: Difficulty
    ): List<Question> {
        return withContext(Dispatchers.IO) {
            // Ensure database is initialized
            initializeDatabase()

            val entities = questionDao.getQuestionsByModuleAndDifficulty(
                moduleType.id,
                difficulty.id
            )
            entities.map { entity ->
                entity.toDomain(
                    optionsArList = entity.optionsAr.split("|"),
                    optionsEnList = entity.optionsEn.split("|")
                )
            }.shuffled() // Randomize questions
        }
    }

    suspend fun getQuestionsByModule(moduleType: ModuleType): List<Question> {
        return withContext(Dispatchers.IO) {
            initializeDatabase()

            val entities = questionDao.getQuestionsByModule(moduleType.id)
            entities.map { entity ->
                entity.toDomain(
                    optionsArList = entity.optionsAr.split("|"),
                    optionsEnList = entity.optionsEn.split("|")
                )
            }
        }
    }
}
