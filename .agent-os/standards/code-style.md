# Code Style Guide

## Context
Global code style rules for Agent OS projects - Syrian Trivia Game (Android/Kotlin).

<conditional-block context-check="general-formatting">
IF this General Formatting section already read in current context:
  SKIP: Re-reading this section
  NOTE: "Using General Formatting rules already in context"
ELSE:
  READ: The following formatting rules

## General Formatting

### Indentation
- Use 4 spaces for indentation (Kotlin convention)
- Never use tabs
- Maintain consistent indentation throughout files
- Align chained method calls vertically

### Naming Conventions
- **Functions and Variables**: Use camelCase (e.g., `userProfile`, `calculateTotal`)
- **Classes and Interfaces**: Use PascalCase (e.g., `TriviaQuestion`, `GameViewModel`)
- **Constants**: Use UPPER_SNAKE_CASE (e.g., `MAX_QUESTIONS_PER_MODULE`, `DEFAULT_DIFFICULTY`)
- **Package names**: Use lowercase (e.g., `com.syriantrivia.game`)
- **Resource files**: Use snake_case (e.g., `activity_main.xml`, `ic_question_mark.xml`)
- **Composable functions**: Use PascalCase (e.g., `QuestionCard`, `DifficultySelector`)

### String Formatting
- Use double quotes for strings: `"Hello World"`
- Use string templates for interpolation: `"Score: $score"`
- Use triple-quoted strings for multi-line text
- Use raw strings for JSON or complex text

### Code Comments
- Add brief comments above non-obvious business logic
- Document complex algorithms (e.g., question randomization logic)
- Explain the "why" behind implementation choices
- Use KDoc format for public APIs and functions
- Never remove existing comments unless removing the associated code
- Update comments when modifying code to maintain accuracy
- Keep comments concise and relevant
- Add TODO comments for temporary solutions or future improvements

### File Organization
- One class per file
- File name should match the class name
- Organize imports alphabetically
- Remove unused imports
- Group related functions together
  </conditional-block>

<conditional-block task-condition="kotlin" context-check="kotlin-style">
IF current task involves writing or updating Kotlin code:
  IF kotlin-style.md already in context:
    SKIP: Re-reading this file
    NOTE: "Using Kotlin style guide already in context"
  ELSE:
    <context_fetcher_strategy>
      IF current agent is Claude Code AND context-fetcher agent exists:
        USE: @agent:context-fetcher
        REQUEST: "Get Kotlin style rules from code-style/kotlin-style.md"
        PROCESS: Returned style rules
      ELSE:
        READ: The following Kotlin style rules
    </context_fetcher_strategy>

## Kotlin Style Rules

### Class Structure
- Order: properties → init blocks → constructors → functions → companion object
- Group related functions together
- Put public members before private ones
- Use data classes for DTOs (e.g., `TriviaQuestion`, `GameState`)

### Properties and Variables
- Prefer `val` (immutable) over `var` (mutable)
- Use property delegation when appropriate (e.g., `by lazy`, `by remember`)
- Initialize properties at declaration when possible
- Use backing properties only when necessary

### Functions
- Keep functions small and focused (single responsibility)
- Use expression body for simple functions: `fun double(x: Int) = x * 2`
- Use named arguments for functions with multiple parameters
- Use default parameters instead of overloading
- Prefer extension functions for utility methods

### Null Safety
- Avoid `!!` operator (use safe calls `?.` or `let` blocks instead)
- Use `?:` (Elvis operator) for default values
- Prefer non-nullable types when possible
- Use `lateinit` sparingly and only when initialization is guaranteed

### Collections
- Prefer immutable collections (`List`, `Set`, `Map`)
- Use collection operations (`map`, `filter`, `fold`) over loops
- Chain operations fluently when it improves readability

### Control Flow
- Prefer `when` over complex `if-else` chains
- Use `if` as an expression when returning values
- Avoid nested conditionals (extract to functions)

### Coroutines
- Use `viewModelScope` for ViewModel coroutines
- Always specify `Dispatchers.IO` for I/O operations
- Use `launch` for fire-and-forget, `async` when you need a result
- Handle exceptions with `try-catch` in coroutines

### Type Inference
- Omit types when they're obvious from context
- Specify types for public APIs
- Specify types when it improves readability

</conditional-block>

<conditional-block task-condition="jetpack-compose" context-check="compose-style">
IF current task involves writing or updating Jetpack Compose UI:
  IF compose-style.md already in context:
    SKIP: Re-reading this file
    NOTE: "Using Compose style guide already in context"
  ELSE:
    <context_fetcher_strategy>
      IF current agent is Claude Code AND context-fetcher agent exists:
        USE: @agent:context-fetcher
        REQUEST: "Get Compose style rules from code-style/compose-style.md"
        PROCESS: Returned style rules
      ELSE:
        READ: The following Compose style rules
    </context_fetcher_strategy>

## Jetpack Compose Style Rules

### Composable Functions
- Name composables as nouns (e.g., `QuestionCard`, not `ShowQuestion`)
- Use PascalCase for all composable function names
- Add `@Composable` annotation on same line as function
- Keep composables small and focused

### State Management
- Use `remember` for simple state
- Use `rememberSaveable` for state that should survive config changes
- Hoist state when multiple composables need to share it
- Use `derivedStateOf` for computed state
- Prefix state-modifying functions with "on" (e.g., `onAnswerSelected`)

### Modifiers
- Apply modifiers in consistent order: size → padding → border → background
- Extract complex modifier chains to variables
- Use `Modifier` parameter as the last parameter with default value

### Previews
- Add `@Preview` for all major composables
- Create preview functions for different states (loading, success, error)
- Use descriptive names for preview functions
- Include previews for both light and dark themes
- Add locale previews for Arabic and English

### Performance
- Avoid unnecessary recomposition (use `remember` wisely)
- Use `key` in `LazyColumn` for stable items
- Avoid creating lambdas in composable bodies (hoist them)
- Use immutable data classes for state

### Styling
- Use Material 3 theme values instead of hardcoded colors
- Extract common spacing values to theme
- Use `stringResource()` for all text (never hardcode strings)
- Support RTL layouts (test with Arabic locale)

</conditional-block>

<conditional-block task-condition="android-resources" context-check="resource-style">
IF current task involves creating or updating Android resources (XML, strings, etc.):
  IF resource-style.md already in context:
    SKIP: Re-reading this file
    NOTE: "Using resource style guide already in context"
  ELSE:
    READ: The following resource style rules

## Android Resources Style Rules

### String Resources
- Use descriptive keys: `question_difficulty_easy` not `easy`
- Group related strings with common prefixes
- Provide translations for both English and Arabic
- Use placeholders for dynamic content: `"Score: %1$d/%2$d"`
- Add context comments for translators when needed

### Drawable Resources
- Use vector drawables (XML) over PNG when possible
- Name icons with `ic_` prefix (e.g., `ic_checkmark.xml`)
- Name backgrounds with `bg_` prefix (e.g., `bg_card_rounded.xml`)
- Use theme attributes for colors in drawables

### Layout Resources (if used)
- Use descriptive IDs: `questionTextView` not `textView1`
- Prefer `ConstraintLayout` for complex layouts
- Use `merge` tag to reduce hierarchy
- Extract common layouts to `<include>` tags

### Dimension Resources
- Define dimensions in `dimens.xml`
- Use consistent spacing scale (4dp, 8dp, 16dp, 24dp, 32dp)
- Name dimensions descriptively: `padding_card`, `text_size_title`

### Color Resources
- Define all colors in `colors.xml`
- Use descriptive names: `syrianGold`, `culturePrimary`
- Never use hex values directly in code
- Support dark theme variants

</conditional-block>

<conditional-block task-condition="json-data" context-check="json-style">
IF current task involves creating or updating JSON data files:
  IF json-style.md already in context:
    SKIP: Re-reading this file
    NOTE: "Using JSON style guide already in context"
  ELSE:
    READ: The following JSON style rules

## JSON Data Style Rules

### Question Format
- Use consistent structure for all questions
- Include all required fields: `id`, `question`, `options`, `correctAnswer`, `difficulty`, `module`
- Use camelCase for field names
- Ensure Arabic text uses proper Unicode encoding
- Keep questions concise and clear

### Module Structure
- One JSON file per module
- Array of question objects
- Include metadata: module name, version, lastUpdated

### Example Structure
```json
{
  "module": "culture",
  "version": "1.0",
  "lastUpdated": "2025-10-01",
  "questions": [
    {
      "id": "culture_001",
      "question": {
        "ar": "من غنى أغنية 'يا مال الشام'؟",
        "en": "Who sang the song 'Ya Mal Al Sham'?"
      },
      "options": [
        {"ar": "صباح فخري", "en": "Sabah Fakhri"},
        {"ar": "فيروز", "en": "Fairuz"},
        {"ar": "كاظم الساهر", "en": "Kadim Al Sahir"},
        {"ar": "وديع الصافي", "en": "Wadih El Safi"}
      ],
      "correctAnswer": 0,
      "difficulty": "easy"
    }
  ]
}
```

### Validation
- Validate JSON structure before committing
- Ensure all questions have exactly 4 options
- Verify correctAnswer index is valid (0-3)
- Check that both language variants exist

</conditional-block>

## Project-Specific Guidelines

### Bilingual Support
- Always provide both Arabic and English strings
- Test UI with both languages
- Ensure RTL layout works correctly with Arabic
- Use locale-appropriate formatting (numbers, dates)

### Syrian Cultural Elements
- Research cultural accuracy for questions
- Use appropriate terminology
- Respect cultural sensitivities
- Incorporate authentic Syrian design elements

### Code Documentation
- Document game logic thoroughly
- Explain randomization algorithms
- Comment on difficulty balancing decisions
- Note any cultural context in code

### Version Control
- Write clear commit messages
- Group related changes in single commits
- Use conventional commit format when possible
- Don't commit commented-out code (delete it)