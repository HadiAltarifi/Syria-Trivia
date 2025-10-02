# Tech Stack

## Context

Global tech stack defaults for Agent OS projects, overridable in project-specific `.agent-os/product/tech-stack.md`.

## Mobile Application

- Platform: Android Native
- Language: Kotlin (latest stable)
- Min SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Build System: Gradle with Kotlin DSL
- Architecture: MVVM (Model-View-ViewModel)
- Dependency Injection: Hilt

## UI/UX

- UI Framework: Jetpack Compose (latest stable)
- Design System: Material 3 with custom Syrian-inspired theme
- Navigation: Jetpack Navigation Compose
- Icons: Material Icons + Custom Syrian cultural icons
- Fonts: Custom Arabic font (e.g., Noto Sans Arabic) + English font
- Animations: Jetpack Compose animations
- RTL Support: Full bidirectional text support

## Data & Storage

- Local Database: Room (SQLite abstraction)
- Data Format: JSON for question/module definitions
- Preferences: DataStore (Jetpack)
- Asset Management: Local JSON files in assets folder
- Data Models: Kotlin data classes with Serialization

## Game Logic

- State Management: Jetpack ViewModel + StateFlow
- Question Pool: JSON-based with random selection algorithm
- Difficulty Levels: Easy, Moderate, Hard (enum-based)
- Module System: Modular JSON structure for easy expansion
- Pass-and-Play: Local multiplayer (no network required)

## Monetization

- Ad Platform: Google AdMob
- Ad Types:
    - Rewarded ads (for hints)
    - Interstitial ads (timed intervals, ~20 minutes)
- Ad Integration: Google Mobile Ads SDK

## Localization

- Framework: Android Resources (strings.xml)
- Primary Language: Arabic (ar)
- Secondary Language: English (en)
- RTL Layout: Automatic with proper locale configuration
- Default Locale: Arabic

## Development Tools

- IDE: Android Studio (latest stable)
- Version Control: Git
- Code Style: ktlint with custom rules
- Testing Framework: JUnit 5 + Compose UI Testing
- JSON Validation: kotlinx.serialization

## Build & Deployment

- Build Variants: debug, release
- Signing: Android keystore
- Distribution: Google Play Store
- CI/CD: GitHub Actions (optional)
- Version Strategy: Semantic versioning

## Third-Party Libraries

- Serialization: kotlinx-serialization-json
- Coroutines: kotlinx-coroutines-android
- Compose BOM: Latest stable version
- AdMob SDK: Latest stable version
- Hilt: Latest stable version

## Performance & Optimization

- Lazy Loading: For question pools
- Memory Management: Efficient bitmap handling for Syrian-themed assets
- Offline-First: All core gameplay works without internet
- Ad Caching: Preload ads for smooth experience