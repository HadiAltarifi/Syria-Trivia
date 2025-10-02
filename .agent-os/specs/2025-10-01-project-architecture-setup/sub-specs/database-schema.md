# Database Schema

This is the database schema implementation for the spec detailed in @.agent-os/specs/2025-10-01-project-architecture-setup/spec.md

## Database Overview

**Database Name:** `syria_trivia_database`
**Version:** 1

## Entities

### QuestionEntity

Stores all trivia questions with bilingual support.

```kotlin
@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey
    val id: String,  // e.g., "culture_001", "history_001"

    @ColumnInfo(name = "module_type")
    val moduleType: String,  // "culture" or "history"

    @ColumnInfo(name = "difficulty")
    val difficulty: String,  // "easy", "moderate", "hard"

    @ColumnInfo(name = "category")
    val category: String,  // e.g., "music", "tv_shows", "wars_battles"

    @ColumnInfo(name = "question_ar")
    val questionAr: String,  // Question text in Arabic

    @ColumnInfo(name = "question_en")
    val questionEn: String,  // Question text in English

    @ColumnInfo(name = "options_ar")
    val optionsAr: String,  // JSON array of Arabic options (stored as string)

    @ColumnInfo(name = "options_en")
    val optionsEn: String,  // JSON array of English options (stored as string)

    @ColumnInfo(name = "correct_answer")
    val correctAnswer: Int  // Index of correct answer (0-3)
)
```

**Indexes:**
- `module_type` - For filtering by module
- `difficulty` - For filtering by difficulty
- Composite index on `(module_type, difficulty)` - For efficient queries

```kotlin
@Entity(
    tableName = "questions",
    indices = [
        Index(value = ["module_type"]),
        Index(value = ["difficulty"]),
        Index(value = ["module_type", "difficulty"])
    ]
)
```

### GameSessionEntity

Stores completed game sessions for future statistics tracking.

```kotlin
@Entity(tableName = "game_sessions")
data class GameSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val sessionId: Long = 0,

    @ColumnInfo(name = "module_type")
    val moduleType: String,  // "culture" or "history"

    @ColumnInfo(name = "difficulty")
    val difficulty: String,  // "easy", "moderate", "hard"

    @ColumnInfo(name = "score")
    val score: Int,  // Number of correct answers

    @ColumnInfo(name = "total_questions")
    val totalQuestions: Int,  // Total number of questions in session

    @ColumnInfo(name = "timestamp")
    val timestamp: Long  // Unix timestamp of session completion
)
```

**Indexes:**
- `timestamp` - For sorting sessions by date

```kotlin
@Entity(
    tableName = "game_sessions",
    indices = [Index(value = ["timestamp"])]
)
```

## Data Access Objects (DAOs)

### QuestionDao

```kotlin
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
```

### GameSessionDao

```kotlin
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
```

## Database Class

```kotlin
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
```

## Type Converters

```kotlin
class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}
```

## Database Initialization Strategy

### First Launch Population

On first app launch, the database is empty and must be populated from JSON files in `assets/questions/`.

**Strategy:**
1. Check if database is empty (query question count)
2. If empty, read all JSON files from assets folder
3. Parse JSON using kotlinx.serialization
4. Transform JSON models to Room entities
5. Insert all questions into database in single transaction

**Implementation Location:** Repository or DatabaseModule

```kotlin
// Pseudocode
suspend fun initializeDatabaseIfNeeded(context: Context, db: SyriaTriviaDatabase) {
    if (db.questionDao().getQuestionCount() == 0) {
        val questions = loadQuestionsFromAssets(context)
        db.questionDao().insertAll(questions)
    }
}
```

### Migration Strategy

**Version 1 → Version 2 (Future)**
- Use Room Migration objects
- Handle schema changes gracefully
- Preserve user data (game sessions)

## Data Integrity Rules

**Questions Table:**
- `id` must be unique (enforced by primary key)
- `correctAnswer` must be between 0 and 3 (validated in application layer)
- `optionsAr` and `optionsEn` must contain exactly 4 items (validated during JSON parsing)
- `moduleType` must be one of: "culture", "history" (validated with enum in application layer)
- `difficulty` must be one of: "easy", "moderate", "hard" (validated with enum in application layer)

**Game Sessions Table:**
- `score` must be ≤ `totalQuestions` (validated in application layer)
- `timestamp` is automatically set to current time when inserting

## Performance Considerations

**Query Optimization:**
- Composite index on `(module_type, difficulty)` ensures fast filtering for the most common query pattern
- Individual indexes on frequently filtered columns
- Use `suspend` functions to avoid blocking UI thread

**Memory Management:**
- Questions are loaded on-demand per module/difficulty (not all at once)
- Game sessions are queried with LIMIT clause to avoid loading entire history
- Use pagination if session history grows large (Phase 2+)

**Database Size:**
- Estimate: ~100-200 questions × ~500 bytes each = ~50-100 KB
- Game sessions: Minimal size, ~50 bytes per session
- Total database size expected: < 1 MB for MVP

## Rationale

**Why Room over raw SQLite?**
- Type safety with compile-time verification
- Automatic query generation
- Seamless Kotlin coroutines integration
- Less boilerplate code
- Easier testing with in-memory databases

**Why store questions in database instead of parsing JSON each time?**
- Faster access (no repeated file I/O and JSON parsing)
- Enables future features like question tracking, analytics
- Allows offline-first architecture
- Easier to query and filter

**Why store options as JSON string instead of separate table?**
- Simpler schema (fewer tables)
- Options always accessed together with question
- Fixed size (always 4 options)
- Easier to maintain for MVP
- Can be normalized later if needed
