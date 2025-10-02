package com.syriantrivia.data.local.assets

import android.content.Context
import com.syriantrivia.data.local.database.QuestionEntity
import kotlinx.serialization.json.Json
import java.io.IOException

class JsonParser(private val context: Context) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun parseQuestions(): List<QuestionEntity> {
        val questions = mutableListOf<QuestionEntity>()

        try {
            // Parse culture.json
            val cultureModule = parseModuleFile("questions/culture.json")
            questions.addAll(cultureModule.questions.map { questionJson ->
                QuestionEntity(
                    id = questionJson.id,
                    moduleType = cultureModule.module,
                    difficulty = questionJson.difficulty,
                    category = questionJson.category,
                    questionAr = questionJson.questionAr,
                    questionEn = questionJson.questionEn,
                    optionsAr = questionJson.optionsAr.joinToString("|"),
                    optionsEn = questionJson.optionsEn.joinToString("|"),
                    correctAnswer = questionJson.correctAnswer
                )
            })

            // Parse history.json
            val historyModule = parseModuleFile("questions/history.json")
            questions.addAll(historyModule.questions.map { questionJson ->
                QuestionEntity(
                    id = questionJson.id,
                    moduleType = historyModule.module,
                    difficulty = questionJson.difficulty,
                    category = questionJson.category,
                    questionAr = questionJson.questionAr,
                    questionEn = questionJson.questionEn,
                    optionsAr = questionJson.optionsAr.joinToString("|"),
                    optionsEn = questionJson.optionsEn.joinToString("|"),
                    correctAnswer = questionJson.correctAnswer
                )
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return questions
    }

    private fun parseModuleFile(fileName: String): ModuleJson {
        val jsonString = try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            throw ioException
        }

        return json.decodeFromString(ModuleJson.serializer(), jsonString)
    }
}
