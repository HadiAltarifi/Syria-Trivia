# Spec Requirements Document

> Spec: Project Architecture Setup & MVP
> Created: 2025-10-01

## Overview

Build a fully playable MVP of Syria Trivia with complete MVVM architecture, Hilt dependency injection, Jetpack Compose UI, and all core gameplay screens, allowing users to select modules, answer questions, and see their results in both Arabic and English.

## User Stories

### Play Trivia Game

As a player, I want to open the app, select a module (Culture or History), choose a difficulty, and answer questions, so that I can test my knowledge of Syrian culture and history.

The player experience should include:
- Home screen with module selection (Culture, History)
- Difficulty selection (Easy, Moderate, Hard)
- Question screen showing 4 answer options
- Immediate feedback on correct/incorrect answers
- Results screen showing final score
- Ability to play again or return to home

### Bilingual Experience

As a bilingual user, I want to switch between Arabic and English, so that I can play in my preferred language with proper text direction.

The bilingual experience should:
- Allow language selection from settings or home screen
- Display all UI text in selected language
- Handle RTL layout for Arabic
- Show questions and answers in selected language

### Learn and Progress

As a player, I want to see my score and which questions I got right or wrong, so that I can learn from my mistakes and track my progress.

The feedback system should:
- Show correct answer when user selects wrong answer
- Display final score as X/Y questions correct
- Allow reviewing all questions and answers after completing module

## Spec Scope

1. **MVVM Architecture Setup** - Configure Model-View-ViewModel pattern with clear layer separation and base classes
2. **Hilt Dependency Injection** - Set up Hilt DI with application component and modules
3. **Jetpack Compose Foundation** - Configure Compose with Material 3 and navigation
4. **Room Database Configuration** - Initialize Room database with entities for questions and game sessions
5. **JSON Parsing Setup** - Implement kotlinx.serialization for parsing question JSON files
6. **Navigation Architecture** - Set up navigation between all screens (Home → Module Selection → Difficulty Selection → Quiz → Results)
7. **Syrian-Inspired Theme System** - Create Material 3 theme with Syrian-inspired colors, fonts, and modern design
8. **Bilingual String Resources** - Complete string resources for Arabic and English with RTL support
9. **Home Screen** - Main landing screen with module selection (Culture, History) and settings
10. **Difficulty Selection Screen** - Choose between Easy, Moderate, Hard difficulty levels
11. **Quiz Screen** - Display question with 4 answer options, show feedback, track progress
12. **Results Screen** - Show final score, correct/incorrect breakdown, option to play again
13. **Question Pool Manager** - Load questions from JSON, filter by module/difficulty, randomize selection
14. **Game Logic** - Track answers, calculate score, prevent duplicate questions in session
15. **Settings Screen** - Language selection (Arabic/English) with immediate UI update

## Out of Scope

- AdMob integration (Phase 2)
- Pass-and-play 1v1 multiplayer mode (Phase 2)
- Hint system with rewarded ads (Phase 2)
- Persistent score tracking across sessions (Phase 2)
- Statistics and history screen (Phase 2)
- Additional modules beyond Culture and History (Phase 3)
- Advanced animations and transitions (Phase 3)
- Sound effects and haptics (Phase 3)

## Expected Deliverable

1. **Fully playable trivia game** - User can launch app, select Culture or History module, choose difficulty, answer all questions (from existing JSON pool), and see final score
2. **Complete UI implementation** - All screens (Home, Difficulty Selection, Quiz, Results, Settings) implemented with Syrian-inspired Material 3 design
3. **Bilingual support working** - Can switch between Arabic and English, all UI updates correctly, RTL layout works for Arabic
4. **Question randomization** - Each play session shows random questions from the selected module/difficulty, no duplicates within same session
5. **Score calculation** - Accurately tracks correct/incorrect answers and displays final score
6. **Database operational** - Questions loaded from JSON into Room database, accessible by repository layer
