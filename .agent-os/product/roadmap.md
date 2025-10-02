# Product Roadmap

## Phase 1: Core MVP

**Goal:** Build the foundational trivia game with essential features for single-player gameplay

**Success Criteria:**
- Users can play Culture and History modules with 2 questions each
- Bilingual support (Arabic/English) working correctly
- Three difficulty levels functional
- Clean, modern Syrian-themed UI

### Features

- [ ] Project structure and architecture setup - Set up MVVM architecture, Hilt DI, and Jetpack Compose foundation `M`
- [ ] JSON question data model and parser - Define data classes and implement JSON parsing with kotlinx.serialization `S`
- [ ] Question pool management system - Random selection algorithm that prevents duplicate questions in same session `M`
- [ ] Basic UI screens - Home screen, module selection, question display, and results screen with Compose `L`
- [ ] Syrian-inspired design system - Custom Material 3 theme with Syrian colors, patterns, and typography `M`
- [ ] Bilingual support implementation - Arabic (RTL) and English strings with locale switching `M`
- [ ] Sample question content - Create 5 questions per module (Culture, History) × 3 difficulties = 30 total questions in JSON `S`

### Dependencies

- Android Studio setup
- Gradle dependencies configured
- Basic JSON schema defined

## Phase 2: Multiplayer & Monetization

**Goal:** Add pass-and-play multiplayer mode and integrate AdMob for monetization

**Success Criteria:**
- Two players can compete locally on same device
- Ads display correctly (hints and timed)
- Score tracking works for both modes
- Ad revenue starts flowing

### Features

- [ ] Pass-and-play 1v1 mode - Turn-based gameplay with player switching and score comparison `L`
- [ ] AdMob integration - Configure SDK, ad units, and implement rewarded ads for hints `M`
- [ ] Timed interstitial ads - Display ads every 20 minutes of active gameplay without disrupting UX `S`
- [ ] Score tracking system - Room database to persist scores, statistics, and player records `M`
- [ ] Hint system - Reveal/eliminate wrong answers as reward for watching ads `S`
- [ ] Results and statistics screen - Display game history, win rates, and performance by module `M`

### Dependencies

- Phase 1 complete
- AdMob account and app registered
- Privacy policy for ad compliance

## Phase 3: Content Expansion & Polish

**Goal:** Expand question pool, add more modules, and refine user experience

**Success Criteria:**
- At least 50 questions per module (10+ questions per module/difficulty)
- Smooth animations and transitions
- App published on Google Play Store
- Positive user feedback on design and gameplay

### Features

- [ ] Expand question database - Add 100+ additional questions across all modules and difficulties `L`
- [ ] New module: Geography - Questions about Syrian cities, landmarks, and regions `M`
- [ ] New module: Language & Proverbs - Arabic phrases, proverbs, and linguistic trivia `M`
- [ ] Animations and transitions - Smooth Compose animations for question reveals, answers, and screen transitions `M`
- [ ] Sound effects and haptics - Audio feedback for correct/wrong answers and vibration on interactions `S`
- [ ] Onboarding flow - First-time user tutorial explaining gameplay and features `S`
- [ ] Google Play Store release - Prepare store listing, screenshots, and publish release build `M`

### Dependencies

- Phase 2 complete
- Content review and fact-checking
- Google Play Developer account
- App signing key
