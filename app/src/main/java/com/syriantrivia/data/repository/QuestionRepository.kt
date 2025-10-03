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
    companion object {
        private const val PREFS_NAME = "question_prefs"
        private const val KEY_QUESTIONS_VERSION = "questions_version"
        private const val CURRENT_QUESTIONS_VERSION = 2 // Increment this when adding new questions
    }

    private var isInitialized = false

    suspend fun initializeDatabase() {
        withContext(Dispatchers.IO) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val savedVersion = prefs.getInt(KEY_QUESTIONS_VERSION, 0)

            // Reload questions if:
            // 1. Database is empty OR
            // 2. Questions version has changed
            if (!isInitialized && (questionDao.getQuestionCount() == 0 || savedVersion < CURRENT_QUESTIONS_VERSION)) {
                // Clear old questions if version changed
                if (savedVersion < CURRENT_QUESTIONS_VERSION && questionDao.getQuestionCount() > 0) {
                    questionDao.deleteAll()
                }

                val jsonParser = JsonParser(context)
                val questions = jsonParser.parseQuestions()
                questionDao.insertAll(questions)

                // Save current version
                prefs.edit().putInt(KEY_QUESTIONS_VERSION, CURRENT_QUESTIONS_VERSION).apply()
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
