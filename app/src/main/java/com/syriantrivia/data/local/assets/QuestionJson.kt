package com.syriantrivia.data.local.assets

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionJson(
    @SerialName("id")
    val id: String,

    @SerialName("question_ar")
    val questionAr: String,

    @SerialName("question_en")
    val questionEn: String,

    @SerialName("options_ar")
    val optionsAr: List<String>,

    @SerialName("options_en")
    val optionsEn: List<String>,

    @SerialName("correct_answer")
    val correctAnswer: Int,

    @SerialName("difficulty")
    val difficulty: String,

    @SerialName("category")
    val category: String
)
