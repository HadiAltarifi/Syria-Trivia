package com.syriantrivia.data.model

data class Question(
    val id: String,
    val moduleType: ModuleType,
    val difficulty: Difficulty,
    val category: String,
    val questionAr: String,
    val questionEn: String,
    val optionsAr: List<String>,
    val optionsEn: List<String>,
    val correctAnswer: Int
) {
    fun getQuestion(isArabic: Boolean): String {
        return if (isArabic) questionAr else questionEn
    }

    fun getOptions(isArabic: Boolean): List<String> {
        return if (isArabic) optionsAr else optionsEn
    }
}
