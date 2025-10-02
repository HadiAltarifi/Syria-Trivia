# Technical Specification

This is the technical specification for the spec detailed in @.agent-os/specs/2025-10-01-project-architecture-setup/spec.md

## Technical Requirements

### Architecture & Project Structure

**Package Organization (Feature-based)**
```
com.syriantrivia
├── data
│   ├── local
│   │   ├── database (Room entities, DAOs, database class)
│   │   └── assets (JSON parser utilities)
│   ├── model (Data classes: Question, Module, GameSession, Difficulty enum)
│   └── repository (QuestionRepository, GameSessionRepository)
├── di (Hilt modules: DatabaseModule, RepositoryModule)
├── ui
│   ├── theme (Material 3 theme, colors, typography, shapes)
│   ├── navigation (NavGraph, Routes, NavHost setup)
│   ├── home (HomeScreen composable + HomeViewModel)
│   ├── difficulty (DifficultyScreen composable + DifficultyViewModel)
│   ├── quiz (QuizScreen composable + QuizViewModel)
│   ├── results (ResultsScreen composable + ResultsViewModel)
│   └── settings (SettingsScreen composable + SettingsViewModel)
└── util (Extension functions, constants, helper classes)
```

**MVVM Pattern Implementation**
- ViewModels extend `ViewModel`, use `viewModelScope` for coroutines
- ViewModels expose `StateFlow<UiState>` for UI observation
- Use sealed classes for UiState (Loading, Success, Error)
- Repositories handle data access, ViewModels handle business logic
- Composables are pure UI functions with no business logic

### Dependency Injection (Hilt)

**Application Setup**
- `@HiltAndroidApp` annotation on Application class
- `@AndroidEntryPoint` annotation on MainActivity
- `@HiltViewModel` annotation on all ViewModels

**Modules Required**
- `DatabaseModule`: Provides Room database instance and DAOs
- `RepositoryModule`: Provides repository instances

### Room Database Schema

**Entities**
- `QuestionEntity`: Stores all questions (id, moduleType, difficulty, questionAr, questionEn, optionsAr, optionsEn, correctAnswer, category)
- `GameSessionEntity`: Stores game session data (sessionId, moduleType, difficulty, score, totalQuestions, timestamp)

**Type Converters**
- String list converter for options arrays
- Enum converter for Module and Difficulty types

**Database Initialization**
- Prepopulate database from JSON files on first app launch
- Use `createFromAsset()` or custom migration with JSON parsing

### JSON Parsing

**Data Models for JSON**
```kotlin
@Serializable
data class QuestionJson(
    val id: String,
    val question_ar: String,
    val question_en: String,
    val options_ar: List<String>,
    val options_en: List<String>,
    val correct_answer: Int,
    val difficulty: String,
    val category: String
)

@Serializable
data class ModuleJson(
    val module: String,
    val title_ar: String,
    val title_en: String,
    val questions: List<QuestionJson>
)
```

**JSON Loading Process**
- Read JSON files from `assets/questions/` folder
- Parse using `kotlinx.serialization.json.Json.decodeFromString()`
- Transform to Room entities and insert into database
- Handle parsing errors with try-catch

### Navigation Architecture

**Route Definitions**
```kotlin
object Routes {
    const val HOME = "home"
    const val DIFFICULTY = "difficulty/{moduleType}"
    const val QUIZ = "quiz/{moduleType}/{difficulty}"
    const val RESULTS = "results/{score}/{total}"
    const val SETTINGS = "settings"
}
```

**Navigation Graph**
- Use `NavHost` in MainActivity setContent
- Define all routes with composable destinations
- Pass parameters via navigation arguments
- Use `navController.navigate()` for screen transitions

### UI/UX Requirements

**Material 3 Theme (Syrian-Inspired)**
```kotlin
// Color Scheme
val SyrianGold = Color(0xFFD4AF37)
val SyrianRed = Color(0xFFCE1126)
val SyrianGreen = Color(0xFF007A3D)
val SyrianBlack = Color(0xFF000000)
val SyrianWhite = Color(0xFFFFFFFF)

// Typography
- Arabic: Noto Sans Arabic (primary), weights 400, 500, 700
- English: Roboto (fallback), weights 400, 500, 700
```

**Screen Specifications**

**HomeScreen**
- Title: "سوريا تريفيا" / "Syria Trivia"
- Module cards: Culture and History with icons
- Settings icon in top bar
- Language toggle button

**DifficultyScreen**
- Module name display
- Three difficulty buttons: Easy, Moderate, Hard
- Back navigation button
- Description text for each difficulty

**QuizScreen**
- Progress indicator (e.g., "Question 3/10")
- Question text (large, centered)
- Four answer option buttons in vertical list
- Visual feedback on selection (correct = green, incorrect = red)
- Next button appears after answer selection
- Display correct answer if user selects wrong answer

**ResultsScreen**
- Score display: "X/Y Correct" with percentage
- Celebration or encouragement message based on score
- "Play Again" button (return to difficulty selection)
- "Home" button (return to home screen)

**SettingsScreen**
- Language selection: Arabic / English radio buttons
- Apply changes immediately (recompose entire app)

### State Management

**QuizViewModel State Example**
```kotlin
data class QuizUiState(
    val currentQuestion: Question? = null,
    val currentQuestionIndex: Int = 0,
    val totalQuestions: Int = 0,
    val selectedAnswer: Int? = null,
    val isAnswerCorrect: Boolean? = null,
    val score: Int = 0,
    val isLoading: Boolean = true,
    val error: String? = null
)
```

**State Flow Pattern**
- Private `_uiState = MutableStateFlow(InitialState)`
- Public `uiState: StateFlow<UiState> = _uiState.asStateFlow()`
- Collect in Composable with `collectAsStateWithLifecycle()`

### Game Logic Requirements

**Question Pool Management**
- Load questions from database filtered by module and difficulty
- Shuffle questions on each game session
- Track asked questions to prevent duplicates within session
- Select configurable number of questions (default 10, but use all available if less than 10)

**Scoring Algorithm**
- 1 point per correct answer
- 0 points for incorrect answer
- Calculate percentage: (score / total) * 100
- Display both fraction and percentage

**Session Flow**
1. User selects module → navigate to difficulty screen
2. User selects difficulty → load questions, navigate to quiz
3. For each question:
   - Display question and options
   - User selects answer
   - Show immediate feedback (correct/incorrect)
   - Show correct answer if user was wrong
   - Update score
   - Navigate to next question or results
4. Display final score and options to replay or return home

### Localization Implementation

**String Resources Structure**
- `res/values/strings.xml` (English)
- `res/values-ar/strings.xml` (Arabic)

**Language Switching**
- Use `LocalConfiguration` in Composable
- Change app locale programmatically on settings change
- Store language preference in DataStore
- Recreate activity to apply locale change

**RTL Support**
- Set `android:supportsRtl="true"` in manifest
- Use `start/end` instead of `left/right` in all layouts
- Test all screens with Arabic locale
- Mirror directional icons (arrows, etc.)

### Performance Optimization

**Database Operations**
- All database queries use `suspend` functions
- Run on `Dispatchers.IO` coroutine dispatcher
- Cache current question pool in ViewModel

**UI Performance**
- Use `remember` for non-changing state
- Use `LazyColumn` with keys if lists are needed
- Avoid unnecessary recomposition
- Keep composables small and focused

**Asset Loading**
- Load questions into database asynchronously on app first launch
- Show loading screen during initial data population
- Cache loaded questions in memory during active game session

## External Dependencies

**kotlinx-serialization-json** (v1.6.0+)
- **Purpose:** Parse JSON question files from assets folder
- **Justification:** Official Kotlin library, type-safe, works seamlessly with Kotlin data classes

**Jetpack Compose BOM** (Latest stable)
- **Purpose:** UI framework for all screens
- **Justification:** Modern Android UI toolkit, official Google recommendation, great for Material 3

**Jetpack Navigation Compose** (v2.7.0+)
- **Purpose:** Screen navigation and routing
- **Justification:** Official navigation solution for Compose, integrates well with ViewModel

**Room** (v2.6.0+)
- **Purpose:** Local database for storing questions and game sessions
- **Justification:** Official Android ORM, type-safe, works with coroutines

**Hilt** (v2.48+)
- **Purpose:** Dependency injection framework
- **Justification:** Official DI solution for Android, reduces boilerplate, great ViewModel integration

**DataStore** (v1.0.0+)
- **Purpose:** Store user preferences (language selection)
- **Justification:** Modern replacement for SharedPreferences, type-safe, coroutine-based

**kotlinx-coroutines-android** (v1.7.0+)
- **Purpose:** Asynchronous operations (database, JSON loading)
- **Justification:** Official Kotlin coroutines library, integrates with ViewModel and Compose
