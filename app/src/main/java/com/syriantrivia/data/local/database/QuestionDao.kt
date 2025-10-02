package com.syriantrivia.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE module_type = :moduleType AND difficulty = :difficulty")
    suspend fun getQuestionsByModuleAndDifficulty(
        moduleType: String,
        difficulty: String
    ): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE module_type = :moduleType")
    suspend fun getQuestionsByModule(moduleType: String): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Query("DELETE FROM questions")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getQuestionCount(): Int
}
