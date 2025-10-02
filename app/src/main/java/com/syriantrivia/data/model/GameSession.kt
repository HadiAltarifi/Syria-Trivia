package com.syriantrivia.data.model

data class GameSession(
    val sessionId: Long = 0,
    val moduleType: ModuleType,
    val difficulty: Difficulty,
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    val percentage: Int
        get() = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
}
