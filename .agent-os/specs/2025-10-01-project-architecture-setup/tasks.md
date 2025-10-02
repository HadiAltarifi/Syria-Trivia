# Spec Tasks

## Tasks

- [ ] 1. Setup Project Dependencies and Configuration
  - [ ] 1.1 Configure build.gradle files with version catalog (libs.versions.toml)
  - [ ] 1.2 Add all required dependencies (Compose, Hilt, Room, Navigation, kotlinx-serialization)
  - [ ] 1.3 Enable Kotlin serialization plugin and Hilt plugin
  - [ ] 1.4 Configure AndroidManifest.xml with RTL support and Application class
  - [ ] 1.5 Verify project builds successfully

- [ ] 2. Implement Data Layer (Models, Database, Repository)
  - [ ] 2.1 Create data models (Question, Module enums, Difficulty enum, GameSession)
  - [ ] 2.2 Create Room entities (QuestionEntity, GameSessionEntity)
  - [ ] 2.3 Create DAOs (QuestionDao, GameSessionDao) with required queries
  - [ ] 2.4 Create Database class with type converters
  - [ ] 2.5 Create JSON models for parsing asset files
  - [ ] 2.6 Implement QuestionRepository with database initialization from JSON
  - [ ] 2.7 Implement GameSessionRepository
  - [ ] 2.8 Create Hilt DatabaseModule and RepositoryModule

- [ ] 3. Implement Syrian-Inspired Theme System
  - [ ] 3.1 Define color scheme (Syrian gold, red, green, black, white)
  - [ ] 3.2 Configure typography with Arabic and English fonts
  - [ ] 3.3 Define shapes and spacing values
  - [ ] 3.4 Create Material 3 theme composable (SyriaTriviaTheme)
  - [ ] 3.5 Create bilingual string resources (values/strings.xml and values-ar/strings.xml)

- [ ] 4. Setup Navigation Architecture
  - [ ] 4.1 Define Routes object with all navigation paths
  - [ ] 4.2 Create NavGraph with all screen destinations
  - [ ] 4.3 Setup NavHost in MainActivity with theme wrapper
  - [ ] 4.4 Verify navigation compiles and basic routing works

- [ ] 5. Implement Home Screen
  - [ ] 5.1 Create HomeViewModel with module selection logic
  - [ ] 5.2 Create HomeScreen composable with module cards (Culture, History)
  - [ ] 5.3 Add settings icon and language display
  - [ ] 5.4 Implement navigation to difficulty screen
  - [ ] 5.5 Add preview functions for light/dark and Arabic/English

- [ ] 6. Implement Difficulty Selection Screen
  - [ ] 6.1 Create DifficultyViewModel
  - [ ] 6.2 Create DifficultyScreen composable with three difficulty buttons
  - [ ] 6.3 Display selected module name
  - [ ] 6.4 Implement navigation to quiz screen with parameters
  - [ ] 6.5 Add back navigation to home

- [ ] 7. Implement Quiz Screen and Game Logic
  - [ ] 7.1 Create QuizViewModel with StateFlow for quiz state
  - [ ] 7.2 Implement question pool loading and randomization logic
  - [ ] 7.3 Implement answer selection and scoring logic
  - [ ] 7.4 Create QuizScreen composable with question display and answer buttons
  - [ ] 7.5 Add progress indicator and question counter
  - [ ] 7.6 Implement visual feedback for correct/incorrect answers
  - [ ] 7.7 Show correct answer when user selects wrong answer
  - [ ] 7.8 Implement navigation to next question or results screen

- [ ] 8. Implement Results Screen
  - [ ] 8.1 Create ResultsViewModel to receive score data
  - [ ] 8.2 Create ResultsScreen composable displaying score and percentage
  - [ ] 8.3 Add celebratory or encouragement message based on score
  - [ ] 8.4 Implement "Play Again" navigation (back to difficulty)
  - [ ] 8.5 Implement "Home" navigation (back to home screen)
  - [ ] 8.6 Save game session to database

- [ ] 9. Implement Settings Screen
  - [ ] 9.1 Create DataStore for language preference
  - [ ] 9.2 Create SettingsViewModel with language selection logic
  - [ ] 9.3 Create SettingsScreen composable with language radio buttons
  - [ ] 9.4 Implement locale switching with immediate UI update
  - [ ] 9.5 Add navigation from home screen to settings

- [ ] 10. Testing and Polish
  - [ ] 10.1 Test complete gameplay flow (Home → Difficulty → Quiz → Results)
  - [ ] 10.2 Test language switching in all screens
  - [ ] 10.3 Test RTL layout with Arabic locale
  - [ ] 10.4 Verify questions load correctly from JSON files
  - [ ] 10.5 Test score calculation accuracy
  - [ ] 10.6 Test question randomization (no duplicates in session)
  - [ ] 10.7 Fix any UI/UX issues or bugs
  - [ ] 10.8 Verify app works on both phone and tablet screen sizes
