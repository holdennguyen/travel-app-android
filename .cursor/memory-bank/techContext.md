# Technical Context: Travel App Android

## Technologies Used
- **Primary Language**: Java
- **Build Scripts**: Kotlin (Gradle DSL)
- **Platform**: Android
- **IDE**: Android Studio
- **Build System**: Gradle
- **Java Version**: Java 21 (target and source compatibility)
- **🔥 MAJOR: Backend Database**: Firebase Realtime Database
- **🔥 MAJOR: Image CDN**: Cloudinary

## Development Setup
- **OS**: macOS (darwin 25.0.0)
- **Shell**: /opt/homebrew/bin/bash
- **Project Path**: `/Users/holden.nguyen/AndroidStudioProjects/travel-app-android`
- **IDE Configuration**: Android Studio with Java 21 support
- **VS Code Integration**: Java configuration for cross-IDE development
- **Firebase Integration**: Firebase Console setup and database.json imported
- **Cloudinary Integration**: Image assets uploaded and CDN configured
- **Cloudinary Account**: holdennguyen6174@gmail.com
- **Cloudinary Folder**: "travel-app-android" (media library organization)

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

**🔥 FIREBASE SDK INTEGRATION (COMPLETED):**
- **Firebase Database**: `com.google.firebase:firebase-database:21.0.0`
- **Google Services Plugin**: `com.google.gms.google-services:4.4.3`
- **Firebase Configuration**: `google-services.json` configured

**Backend Services (External):**
- **Firebase Realtime Database**: Real-time data sync and offline support
- **Cloudinary**: Professional image management and CDN delivery

**Repository Configuration:**
- **JitPack**: Added for Chip Navigation Bar dependency
- **Google Maven**: For Firebase dependencies
- **Maven Central**: Standard Android dependencies

## Technical Constraints
- **Minimum SDK**: TBD (based on Firebase SDK requirements)
- **Target SDK**: TBD (based on Firebase SDK requirements)
- **Java Version**: 21 (compile target)
- **View Binding**: Enabled for all modules
- **Firebase Rules**: Database security rules need implementation
- **Cloudinary Limits**: API usage and storage limits per plan
- **Google Services**: Requires google-services.json configuration

## Backend Integration Architecture
**Firebase Realtime Database:**
- **Status**: ✅ **COMPLETE** - Connected, database.json imported, Android SDK integrated
- **Android SDK**: `firebase-database:21.0.0` with Google Services Plugin 4.4.3
- **Configuration**: `google-services.json` added to app module
- **Features**: Real-time sync, offline support, JSON-based NoSQL
- **Integration**: Ready for data models and repository implementation

**Cloudinary CDN:**
- **Status**: Images uploaded and integrated with Firebase
- **Account**: holdennguyen6174@gmail.com
- **Media Library Folder**: "travel-app-android"
- **Features**: Image optimization, transformation, global CDN
- **Integration**: URLs stored in Firebase, ready for Glide loading
- **Organization**: All travel app images organized in dedicated folder

## Database/Storage Strategy
- **Primary Data**: Firebase Realtime Database (real-time sync with SDK integration)
- **Images**: Cloudinary CDN (optimized delivery from "travel-app-android" folder)
- **Local Cache**: Firebase local persistence (offline support)
- **Image Cache**: Glide automatic caching (performance)
- **App Settings**: SharedPreferences for local app configuration

## Network/Connectivity Patterns
- **Real-time Data**: Firebase WebSocket connections for live updates (SDK integrated)
- **Image Loading**: Cloudinary CDN via Glide HTTP requests (from travel-app-android folder)
- **Offline Support**: Firebase local persistence maintains app functionality
- **Data Sync**: Automatic sync when connection restored

## Tool Usage Patterns
- **Android Studio**: Primary IDE for development
- **Gradle**: Build automation with Kotlin DSL
- **Git**: Version control with comprehensive commit history
- **VS Code**: Optional secondary IDE with Java support
- **ViewBinding**: Type-safe view access replacing findViewById()
- **Firebase Console**: Database management and monitoring
- **Cloudinary Dashboard**: Image asset management and analytics (travel-app-android folder)
- **Version Catalog**: Centralized dependency management for Firebase and other libraries

## Development Workflow
- **Memory Bank**: Documentation-first development approach
- **Commit Strategy**: Detailed commits with comprehensive descriptions
- **Backend-First**: Established complete backend infrastructure before app integration
- **SDK Integration**: Firebase Android SDK now enables live data development and testing
- **Version Management**: Using Gradle version catalog for consistent dependency management

## Deployment Strategy
- **Target**: Google Play Store
- **Build Variants**: Debug, Release
- **ProGuard**: Enabled for release builds
- **Firebase Environment**: Production database with proper security rules
- **Cloudinary**: Production CDN with optimized delivery settings (travel-app-android folder)
- **Google Services**: Production google-services.json configuration 