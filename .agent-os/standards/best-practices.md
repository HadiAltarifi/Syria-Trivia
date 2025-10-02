# Development Best Practices

## Context
Global development guidelines for Agent OS projects - Syrian Trivia Game (Android/Kotlin).

<conditional-block context-check="core-principles">
IF this Core Principles section already read in current context:
  SKIP: Re-reading this section
  NOTE: "Using Core Principles already in context"
ELSE:
  READ: The following principles

## Core Principles

### Keep It Simple
- Implement code in the fewest lines possible
- Avoid over-engineering solutions
- Choose straightforward approaches over clever ones
- Start with the simplest working solution, then optimize if needed

### Optimize for Readability
- Prioritize code clarity over micro-optimizations
- Write self-documenting code with clear variable names
- Add comments for "why" not "what"
- Use Kotlin idioms (when, data classes, extension functions)

### DRY (Don't Repeat Yourself)
- Extract repeated business logic to private methods
- Extract repeated UI patterns to reusable composables
- Create utility functions for common operations (e.g., question shuffling)
- Use extension functions for repeated transformations

### File Structure
- Keep files focused on a single responsibility
- Group related functionality together (e.g., all Culture module logic)
- Use consistent naming conventions
- Organize by feature, not by layer
  </conditional-block>

<conditional-block context-check="architecture" task-condition="writing-android-code">
IF current task involves writing Android application code:
  IF Architecture section already read in current context:
    SKIP: Re-reading this section
    NOTE: "Using Architecture guidelines already in context"
  ELSE:
    READ: The following guidelines
ELSE:
  SKIP: Architecture section not relevant to current task

## Architecture

### MVVM Pattern
- **Model**: Data classes and repositories for questions
- **ViewModel**: Business logic, state management, game rules
- **View**: Composable functions (UI only, no business logic)
- Keep ViewModels platform-agnostic (no Android imports except lifecycle)

### State Management
- Use `StateFlow` for UI state in ViewModels
- Use `MutableStateFlow` privately, expose as `StateFlow`
- Represent UI state with sealed classes or data classes
- Handle loading, success, error states explicitly

### Data Layer
- Room database for question storage and game progress
- Repository pattern for data access abstraction
- Use suspend functions for database operations
- Cache questions in memory for active gameplay

### UI Layer
- Pure Composables (no side effects)
- Hoist state to appropriate level
- Pass callbacks down, not ViewModels
- Use `collectAsStateWithLifecycle()` for StateFlow observation

### Navigation
- Use Jetpack Navigation Compose
- Define routes as constants
- Pass minimal data through navigation (IDs, not full objects)
- Handle deep links if needed for specific modules

</conditional-block>

<conditional-block context-check="dependencies" task-condition="choosing-external-library">
IF current task involves choosing an external library:
  IF Dependencies section already read in current context:
    SKIP: Re-reading this section
    NOTE: "Using Dependencies guidelines already in context"
  ELSE:
    READ: The following guidelines
ELSE:
  SKIP: Dependencies section not relevant to current task

## Dependencies

### Choose Libraries Wisely
When adding third-party dependencies:
- Select the most popular and actively maintained option
- Check the library's GitHub repository for:
  - Recent commits (within last 6 months)
  - Active issue resolution
  - Number of stars/downloads
  - Clear documentation
- Prefer Jetpack/AndroidX libraries when available
- Consider library size impact on APK

### Essential Dependencies Only
- Don't add dependencies for simple functionality you can write
- Avoid deprecated libraries
- Keep dependency count minimal
- Regular audit for unused dependencies

### Version Management
- Use version catalogs (libs.versions.toml)
- Keep dependencies up to date (monthly checks)
- Test thoroughly after major version updates
- Pin versions for stability
  </conditional-block>

<conditional-block context-check="testing" task-condition="writing-tests">
IF current task involves writing or updating tests:
  IF Testing section already read in current context:
    SKIP: Re-reading this section
    NOTE: "Using Testing guidelines already in context"
  ELSE:
    READ: The following guidelines
ELSE:
  SKIP: Testing section not relevant to current task

## Testing

### Test Strategy
- Unit tests for ViewModels and business logic
- UI tests for critical user flows
- Test question randomization algorithms
- Test scoring calculations
- Test difficulty filtering

### Unit Testing
- Use JUnit 5 for unit tests
- Mock dependencies with MockK
- Test edge cases (empty question pools, invalid answers)
- Test both languages work correctly
- Aim for >70% code coverage on business logic

### UI Testing
- Use Compose Testing library
- Test user interactions (answer selection, navigation)
- Test state changes are reflected in UI
- Test both RTL and LTR layouts
- Test with both Arabic and English locales

</conditional-block>

<conditional-block context-check="performance" task-condition="performance-consideration">
IF current task involves performance-critical code:
  IF Performance section already read in current context:
    SKIP: Re-reading this section
    NOTE: "Using Performance guidelines already in context"
  ELSE:
    READ: The following guidelines
ELSE:
  SKIP: Performance section not relevant to current task

## Performance

### Memory Management
- Avoid memory leaks (use lifecycle-aware components)
- Don't hold references to Activity/Context in ViewModels
- Clear resources in onCleared() when needed
- Use weak references for callbacks if necessary

### Database Optimization
- Index frequently queried columns (difficulty, module)
- Use pagination for large question sets
- Lazy load questions as needed
- Cache active question pool in memory during gameplay

### UI Performance
- Avoid unnecessary recomposition
- Use `remember` and `derivedStateOf` appropriately
- Keep composables small and focused
- Use `LazyColumn` key parameter for stable lists
- Profile with Layout Inspector when needed

### Asset Loading
- Compress images appropriately
- Use vector drawables when possible
- Lazy load module-specific assets
- Preload next question during current question display

### Ad Performance
- Preload ads in background
- Cache ads to avoid gameplay interruption
- Handle ad loading failures gracefully
- Don't block UI thread with ad operations
  </conditional-block>

<conditional-block context-check="localization" task-condition="bilingual-work">
IF current task involves bilingual content or localization:
  IF Localization section already read in current context:
    SKIP: Re-reading this section
    NOTE: "Using Localization guidelines already in context"
  ELSE:
    READ: The following guidelines
ELSE:
  SKIP: Localization section not relevant to current task

## Localization & Internationalization

### String Resources
- Never hardcode strings in code
- Always use `stringResource()` in Composables
- Provide strings.xml for both `values/` (English) and `values-ar/` (Arabic)
- Use plurals for count-dependent strings
- Use string formatting for dynamic values

### RTL Support
- Test all screens with Arabic locale
- Use `start`/`end` instead of `left`/`right` in layouts
- Mirror directional icons (back/forward arrows)
- Ensure text alignment adapts to locale
- Use `LocalLayoutDirection` in Compose when needed

### Cultural Considerations
- Validate Arabic translations with native speakers
- Use formal Modern Standard Arabic for questions
- Consider regional dialect variations for informal content
- Research historical accuracy for history questions
- Ensure culturally appropriate imagery and colors

### Date and Number Formatting
- Use locale-appropriate number formatting
- Format dates according to locale
- Handle Arabic numerals vs Eastern Arabic numerals
- Use ICU formatting when needed
  </conditional-block>

<conditional-block context-check="security" task-condition="handling-sensitive-data">
IF current task involves security or sensitive data:
  IF Security section already read in current context:
    SKIP: Re-reading this section
    NOTE: "Using Security guidelines already in context"
  ELSE:
    READ: The following guidelines
ELSE:
  SKIP: Security section not relevant to current task

## Security & Privacy

### Data Storage
- Store all data locally (no cloud sync initially)
- Use encrypted SharedPreferences for sensitive settings
- Don't log personally identifiable information
- Clear sensitive data when app is uninstalled

### AdMob Integration
- Follow AdMob policies strictly
- Request minimal permissions
- Respect user privacy settings
- Implement consent management if required
- Handle ad IDs according to privacy requirements

### Permissions
- Request only necessary permissions
- Explain why permissions are needed
- Handle permission denials gracefully
- No location tracking or unnecessary data collection
  </conditional-block>

<conditional-block context-check="monetization" task-condition="ad-implementation">
IF current task involves ads or monetization:
  IF Monetization section already read in current context:
    SKIP: Re-reading this section
    NOTE: "Using Monetization guidelines already in context"
  ELSE:
    READ: The following guidelines
ELSE:
  SKIP: Monetization section not relevant to current task

## Monetization (AdMob)

### Ad Implementation
- Rewarded ads for hints (optional for users)
- Interstitial ads every 20 minutes (non-intrusive timing)
- Never interrupt active gameplay with ads
- Show ads between modules or after completing sessions
- Implement ad frequency capping

### User Experience
- Make ads skippable when possible
- Provide clear "watch for hint" option
- Don't force ads on users mid-question
- Handle ad load failures gracefully (continue without ad)
- Test ad experience thoroughly

### Ad Loading Strategy
```kotlin
// Preload ads in background
viewModelScope.launch(Dispatchers.IO) {
    adManager.preloadInterstitialAd()
}

// Show at appropriate times
if (shouldShowAd() && adManager.isAdReady()) {
    adManager.showAd(activity)
}
```

### Compliance
- Follow Google AdMob policies
- Implement required privacy disclosures
- Don't click your own ads during testing
- Use test ad units during development
  </conditional-block>

## Project-Specific Best Practices

### Question Management
- Keep question JSON files clean and validated
- Use IDs for tracking (e.g., `culture_001`, `history_001`)
- Version question sets for future updates
- Log analytics on question difficulty/success rates

### Game Logic
- Randomize question order each play session
- Avoid showing same question twice in one session
- Track player progress per module
- Save incomplete sessions (allow resume)

### Module Extensibility
- Design for easy addition of new modules
- Use consistent module structure
- Make module metadata easy to define
- Support hot-swapping question files

### Pass-and-Play Mode
- Clear UI indication of whose turn it is
- Track scores separately per player
- Simple turn-based flow
- No network dependencies

### Syrian Cultural Authenticity
- Research questions thoroughly
- Cite sources for historical facts
- Collaborate with cultural consultants when possible
- Avoid controversial political topics
- Celebrate positive aspects of Syrian heritage

### Version Control
- Commit frequently with clear messages
- Use feature branches for new modules
- Tag releases properly
- Keep main branch stable
- Document breaking changes in commits

### Code Reviews (if applicable)
- Review for cultural accuracy
- Check both language versions
- Test RTL layout
- Verify accessibility
- Performance check on lower-end devices