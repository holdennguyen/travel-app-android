# Technical Context: Travel App Android

## Technologies Used
- **Primary Language**: Java
- **Build Scripts**: Kotlin (Gradle DSL)
- **Platform**: Android
- **IDE**: Android Studio
- **Build System**: Gradle
- **Java Version**: Java 21 (target and source compatibility)

## Development Setup
- **OS**: macOS (darwin 25.0.0)
- **Shell**: /opt/homebrew/bin/bash
- **Project Path**: `/Users/holden.nguyen/AndroidStudioProjects/travel-app-android`
- **IDE Configuration**: Android Studio with Java 21 support
- **VS Code Integration**: Java configuration for cross-IDE development

## Current Dependencies
**Core Android Dependencies:**
- Android SDK
- Gradle build system
- Standard Android testing libraries (JUnit, Espresso)

**UI & Navigation Libraries:**
- **ViewBinding**: Built-in Android feature for type-safe view access
- **Chip Navigation Bar**: `com.github.ismaeldivita:chip-navigation-bar:1.4.0`
- **ViewPager2**: `androidx.viewpager2:viewpager2:1.0.0`

**Image Loading:**
- **Glide**: `com.github.bumptech.glide:glide:4.16.0`

## Technical Constraints
- **Minimum SDK**: TBD (based on standard Android project template)
- **Target SDK**: TBD (based on standard Android project template)
- **Java Version**: 21 (compile target)
- **View Binding**: Enabled for all modules

## API Integration Requirements
*To be defined based on travel app functionality needs*

## Database/Storage Strategy
*To be defined based on data persistence requirements*
- Likely candidates: Room database for local storage
- SharedPreferences for app settings

## Network/Connectivity Patterns
*To be defined based on API integration and offline capabilities*
- Glide handles image loading from network sources
- Future: Retrofit for API integration

## Tool Usage Patterns
- **Android Studio**: Primary IDE for development
- **Gradle**: Build automation with Kotlin DSL
- **Git**: Version control with comprehensive commit history
- **VS Code**: Optional secondary IDE with Java support
- **ViewBinding**: Type-safe view access replacing findViewById()

## Development Workflow
- **Memory Bank**: Documentation-first development approach
- **Commit Strategy**: Detailed commits with comprehensive descriptions
- **UI-First**: Establishing UI framework before core features

## Deployment Strategy
*To be defined based on distribution requirements (Google Play, etc.)*
- Target: Google Play Store
- Build variants: Debug, Release
- ProGuard: Enabled for release builds 