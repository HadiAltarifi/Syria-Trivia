package com.syriantrivia.ui.navigation

object Routes {
    const val HOME = "home"
    const val DIFFICULTY = "difficulty/{moduleType}"
    const val QUIZ = "quiz/{moduleType}/{difficulty}"
    const val RESULTS = "results/{score}/{total}"
    const val SETTINGS = "settings"

    fun difficulty(moduleType: String) = "difficulty/$moduleType"
    fun quiz(moduleType: String, difficulty: String) = "quiz/$moduleType/$difficulty"
    fun results(score: Int, total: Int) = "results/$score/$total"
}
