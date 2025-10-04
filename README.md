<img width=" 1280" height="640" alt="mockuper" src="https://github.com/user-attachments/assets/c85c9571-21f3-4525-a39f-46ce016738ce" />
-----

# 📈 Stock Market App

A sample stock market application for Android, built entirely with modern technologies like kotlin,Jetpack Compose, Coroutines, Flow, and Hilt. This project serves as a showcase of modern Android development practices, including a clean architecture and a reactive UI.

-----

## ✨ Features

  * **Top Movers Home Screen**: Displays top gainers, losers, and most actively traded stocks in a 2x2 scrollable grid format along with see more button.
  * **Company Listings**: A searchable list of all available stocks with pull-to-refresh functionality and the list is locally cached using room .
  * **Company Details**: A detailed screen for each company, showing its profile, description, and an interactive stock chart.
  * **Watchlist Management**:
      * Create multiple, named watchlists.
      * Add stocks to any watchlist from the company details screen via a bottom sheet.
      * View all watchlists in an expandable/collapsible list.
      * Delete individual stocks from a watchlist.
      * Delete entire watchlists with a confirmation dialog.
  * **Modern UI**: Built with Material 3, featuring a floating capsule-shaped navigation bar and dynamic, scroll-aware top app bars.

-----

## 📸 Screenshots
<img width="300" alt="Screenshot3" src="https://github.com/user-attachments/assets/42ac3c8e-f24f-4d4f-8aed-5a7f7040eb33" />
<img width="300" alt="Screenshot2" src="https://github.com/user-attachments/assets/46030251-1c64-441e-beaa-c9cd2201057f" />
<img width="300" alt="Screenshot4" src="https://github.com/user-attachments/assets/0856b4b9-5d4f-40ef-ac2b-9f4411fb8c61" />
<img width="300" alt="Screenshot1" src="https://github.com/user-attachments/assets/ccfe8f3a-472a-4081-a465-8f82a7c5c434" />

-----

## 🛠️ Tech Stack & Architecture

### Tech Stack

  * **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material 3.
  * **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/flow.html).
  * **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).
  * **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/).
  * **Data Parsing**: [Moshi](https://github.com/square/moshi) (for JSON) and a custom CSV parser.
  * **Database**: [Room](https://developer.android.com/training/data-storage/room) for caching and watchlist storage.
  * **Navigation**: [Jetpack Navigation for Compose](https://developer.android.com/jetpack/compose/navigation).

### Architecture

  * **Clean Architecture**: Follows a clear separation of concerns between UI, Domain, and Data layers.
  * **MVVM (Model-View-ViewModel)**: For separating UI logic from business logic.
  * **MVI-like State Management**: The UI is a function of a single `State` object, and all user actions are handled as `Events`.
  * **Repository Pattern**: A single source of truth for all application data.

-----

## 🏗️ Setup and Installation

To build and run this project, you will need to get a free API key from [Alpha Vantage](https://www.alphavantage.co/).

1.  Clone the repository:
    ```bash
    git clone https://github.com/your-username/your-repo-name.git
    ```
2.  Open the project in Android Studio.
3.  **Add your API key**: Create a new file in `app/src/main/java/com/example/stock_app/Constants.kt`.
4.  Add your key to this file like so:
    ```kotlin
    // In ApiConstants.kt
    package com.example.stock_app

    const val API_KEY = "YOUR_API_KEY_HERE"
    ```
5.  Build and run the app.

-----
