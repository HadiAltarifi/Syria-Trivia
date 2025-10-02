package com.syriantrivia.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameSessionDao {
    @Insert
    suspend fun insertSession(session: GameSessionEntity): Long

    @Query("SELECT * FROM game_sessions ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentSessions(limit: Int = 10): List<GameSessionEntity>

    @Query("SELECT * FROM game_sessions WHERE module_type = :moduleType ORDER BY timestamp DESC")
    suspend fun getSessionsByModule(moduleType: String): List<GameSessionEntity>

    @Query("DELETE FROM game_sessions")
    suspend fun deleteAll()
}
