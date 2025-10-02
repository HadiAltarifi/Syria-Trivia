package com.syriantrivia.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.syriantrivia.data.model.Difficulty
import com.syriantrivia.data.model.GameSession
import com.syriantrivia.data.model.ModuleType

@Entity(
    tableName = "game_sessions",
    indices = [Index(value = ["timestamp"])]
)
data class GameSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val sessionId: Long = 0,

    @ColumnInfo(name = "module_type")
    val moduleType: String,

    @ColumnInfo(name = "difficulty")
    val difficulty: String,

    @ColumnInfo(name = "score")
    val score: Int,

    @ColumnInfo(name = "total_questions")
    val totalQuestions: Int,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long
) {
    fun toDomain(): GameSession {
        return GameSession(
            sessionId = sessionId,
            moduleType = ModuleType.fromId(moduleType),
            difficulty = Difficulty.fromId(difficulty),
            score = score,
            totalQuestions = totalQuestions,
            timestamp = timestamp
        )
    }

    companion object {
        fun fromDomain(session: GameSession): GameSessionEntity {
            return GameSessionEntity(
                sessionId = session.sessionId,
                moduleType = session.moduleType.id,
                difficulty = session.difficulty.id,
                score = session.score,
                totalQuestions = session.totalQuestions,
                timestamp = session.timestamp
            )
        }
    }
}
