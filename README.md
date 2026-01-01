# User Selection App (MVI Focused)

A modern Android application built using **Jetpack Compose**, **Clean Architecture**, and **MVVM**.

## Architecture
- Clean Architecture
    - Domain
    - Data
    - Presentation (MVI)

- MVI in Presentation Layer
    - Intent → User actions
    - State → UI state
    - Event → One-time side effects

## Tech Stack
- Kotlin
- Jetpack Compose
- StateFlow & SharedFlow
- Hilt (Dependency Injection)
- Navigation Component
- Unit Testing (ViewModel)

## Features
- User list with select / deselect
- Search users (minimum 3 characters)
- Sticky submit button
- Show selected users on submit
- User detail screen
- Unidirectional data flow using MVI
- State management with StateFlow
- One-time events with SharedFlow

## Key Learnings
- Managing UI state using StateFlow
- One-time events using SharedFlow
- Compose navigation with arguments
- Clean separation of concerns
- Dependency Injection with Hilt
- Practical understanding of MVI
- Difference between MVVM vs MVI
- Handling state, events, and side effects

## How to Run
1. Clone the repository
2. Open in Android Studio
3. Run on emulator or device

---