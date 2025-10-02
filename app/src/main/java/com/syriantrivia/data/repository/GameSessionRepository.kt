package com.syriantrivia.data.repository

import com.syriantrivia.data.local.database.GameSessionDao
import com.syriantrivia.data.local.database.GameSessionEntity
import com.syriantrivia.data.model.GameSession
import com.syriantrivia.data.model.ModuleType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSessionRepository @Inject constructor(
    private val gameSessionDao: GameSessionDao
) {
    suspend fun saveSession(session: GameSession): Long {
        return withContext(Dispatchers.IO) {
            val entity = GameSessionEntity.fromDomain(session)
            gameSessionDao.insertSession(entity)
        }
    }

    suspend fun getRecentSessions(limit: Int = 10): List<GameSession> {
        return withContext(Dispatchers.IO) {
            gameSessionDao.getRecentSessions(limit).map { it.toDomain() }
        }
    }

    suspend fun getSessionsByModule(moduleType: ModuleType): List<GameSession> {
        return withContext(Dispatchers.IO) {
            gameSessionDao.getSessionsByModule(moduleType.id).map { it.toDomain() }
        }
    }
}
