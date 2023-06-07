# Dictionary App 
It is an English dictionary application developed on MVVM and Clean architecture architectural approach.

This application allows users to search English words as they wish and learn their meanings. Additionally, users can save any desired word. Moreover, the application can send daily English word notifications to the user's phone.

## Layers
* Core Layer -> It contains some UI components and structures that will be used for network requests, but it is separate from the business logic of the application.
* Navigation Layer -> The screens present in the application and the navigation and state information triggered by user interaction are kept in this layer.
* Feature Layer
    - Data -> Here, models are available for the local database, parsers, and data from the remote source.
    - DI -> It is a separated layer in the application that aims to eliminate external dependencies and automate these dependencies. 
The DI layer helps provide high flexibility, independence, and testability to the application components. Additionally, it prevents code duplication and enables the development of components in a more modular manner.
    - Domain -> 
The domain layer of the application includes the fundamental and independent components such as the main logic, business rules, and data models. This layer does not communicate with other layers and remains independent from external dependencies.
    - Notification -> The layer responsible for setting the notification to be sent to users.
    - Presentation -> It is the layer to interact with the user. This layer receives user inputs, triggers related actions and displays the results.


## Used Libraries and Technologies
* All developed with kotlin language.
* [Jetpack Compose](https://developer.android.com/jetpack/compose) was used for UI development.
* Android Architecture Components
  - A single-activity architecture, using the Navigation to manage composable transactions.
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) -> perform an action when lifecycle state change.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) -> Stores UI-related data that isn't destroyed on UI changes.
  - [UseCases](https://developer.android.com/topic/architecture/domain-layer) -> Located domain layer that sits between the UI layer and the data layer.
  - [Repository](https://developer.android.com/topic/architecture/data-layer) -> Located in data layer that contains application data and business logic.
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) -> DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally. 
 * [Coroutines + Flow](https://developer.android.com/kotlin/flow) -> for asynchronous data streams.
 * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) -> For Dependency Injection.
 * [Retrofit2 & OkHttp3](https://square.github.io/retrofit/) -> construct the REST APIs and paging network data.
 * [Room](https://developer.android.com/jetpack/androidx/releases/room) -> saving data for local database.
 * [Navigation](https://developer.android.com/jetpack/compose/navigation) -> Navigating between screens.
 * [Coil](https://coil-kt.github.io/coil/) -> An image loading library for Android backed by Kotlin Coroutines.
 * [Splash API](https://developer.android.com/develop/ui/views/launch/splash-screen)
 * [Accompanist](https://github.com/google/accompanist)
