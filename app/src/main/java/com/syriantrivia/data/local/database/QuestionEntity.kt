package com.syriantrivia.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.syriantrivia.data.model.Difficulty
import com.syriantrivia.data.model.ModuleType
import com.syriantrivia.data.model.Question

@Entity(
    tableName = "questions",
    indices = [
        Index(value = ["module_type"]),
        Index(value = ["difficulty"]),
        Index(value = ["module_type", "difficulty"])
    ]
)
data class QuestionEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "module_type")
    val moduleType: String,

    @ColumnInfo(name = "difficulty")
    val difficulty: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "question_ar")
    val questionAr: String,

    @ColumnInfo(name = "question_en")
    val questionEn: String,

    @ColumnInfo(name = "options_ar")
    val optionsAr: String, // JSON array as string

    @ColumnInfo(name = "options_en")
    val optionsEn: String, // JSON array as string

    @ColumnInfo(name = "correct_answer")
    val correctAnswer: Int
) {
    fun toDomain(optionsArList: List<String>, optionsEnList: List<String>): Question {
        return Question(
            id = id,
            moduleType = ModuleType.fromId(moduleType),
            difficulty = Difficulty.fromId(difficulty),
            category = category,
            questionAr = questionAr,
            questionEn = questionEn,
            optionsAr = optionsArList,
            optionsEn = optionsEnList,
            correctAnswer = correctAnswer
        )
    }

    companion object {
        fun fromDomain(question: Question): QuestionEntity {
            return QuestionEntity(
                id = question.id,
                moduleType = question.moduleType.id,
                difficulty = question.difficulty.id,
                category = question.category,
                questionAr = question.questionAr,
                questionEn = question.questionEn,
                optionsAr = question.optionsAr.joinToString("|"), // Simple delimiter
                optionsEn = question.optionsEn.joinToString("|"),
                correctAnswer = question.correctAnswer
            )
        }
    }
}
