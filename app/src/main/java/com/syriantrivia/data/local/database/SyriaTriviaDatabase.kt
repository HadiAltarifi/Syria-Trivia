package com.syriantrivia.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [QuestionEntity::class, GameSessionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SyriaTriviaDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun gameSessionDao(): GameSessionDao
}
