# Android Challenge

Overview

This project provides an efficient city search functionality built using Jetpack Compose and modern Android architecture principles. The app offers a seamless experience with the ability to filter favorite cities, paginated results, and efficient queries for faster data retrieval.

##### Features

	•	Local Database: All cities are stored locally using Room, ensuring offline access and faster query execution.
	•	Efficient Search: Supports case-insensitive search with prefix and substring matching for city names and country codes.
	•	Favorites Management: Users can mark cities as favorites, and the search results dynamically update to include only favorite cities when the filter is enabled.
	•	Pagination: Data is fetched in smaller chunks for a smoother scrolling experience, reducing memory overhead.
	•	Dynamic UI: The UI adapts based on user inputs like search queries and favorite filters, ensuring real-time updates.

## Requirements

  - **Java SDK 17**:
    Ensure Java 17 is installed and properly configured on your system. This project requires Java 17 for compatibility.

  - **Gradle**: 
  The project uses Gradle Wrapper (version 8.7).

  - **GoogleMapsAPI**:
    •	You need a Google Maps API Key to enable map functionality. Ensure the API Key is configured in your AndroidManifest.xml.
    •	If you do not have an API Key, contact ferpai@gmail.com to request one. Note: The provided API Key is restricted using SHA-1 for security.

## Approach to Solve the Search Problem

#### 1. Preprocessing Data

To improve search performance, all cities are downloaded from the API during the app’s first launch and saved in a local database. This approach minimizes network calls and allows for rapid query execution using SQLite.

Why Local Storage?

    •	Faster search as data resides on the device.
    •	Offline capability for better user experience.

#### 2. Database Optimization

    •	Indexes: The name and country columns are indexed to speed up search queries.
    •	Custom Query Logic:
      A composite query supports both prefix and substring matches.
      Case-insensitivity ensures user-friendly searches.

#### 3. Unified Search and Pagination

The search and pagination logic have been unified under a single use case to ensure scalability, reduce redundancy, and enhance maintainability. This design allows for seamless filtering and incremental data loading.

Key benefits:

    •	Improved Performance: Pagination ensures data is loaded incrementally, reducing memory consumption and enhancing responsiveness.
    •	Scalability: Combines search logic with pagination (getPagedCities and searchCitiesPaginated) for efficient filtering and display.
    •	Maintainability: Centralizing all city-related logic under a single use case simplifies the codebase and avoids redundant logic.

### Technical Highlights

Tech Stack

	•	Architecture: MVVM (Model-View-ViewModel)
	•	Dependency Injection: Hilt
	•	Database: Room with Paging
	•	UI Framework: Jetpack Compose
	•	API: Retrofit with Kotlin Coroutines
