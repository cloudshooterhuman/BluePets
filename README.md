<p align="center">
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/ic_launcher.png" width="50" title="Home screen">
</p>

## Objectives

- **Clean Architecture**: presentation, domain (agostic), data layers, and dependency injection (Hilt). [ref](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/)

<p align="center">
<img src="https://github.com/selmanon/BluePets/assets/2206036/896dd709-0d63-41b1-a060-475225b6e4bc" alt="drawing" width="700"/>
</p>
<p align="center">
 Dependencies between Layers
</p> 


- **Unit Testing**: with JUnit and MockK.

- **Data Flow Management**: Coroutine, Flow, and Paging.

- **View Management**: Jetpack Compose (Dark/Night).

- **Navigation Management**: Jetpack Navigation.

## Implementation Breakdown

### The Domain Layer

>The domain is the application's core containing the business logic and rules. The domain needs to be independent of other modules and libraries related to the UI, Android, etc. All classes (except models) and public >methods should be tested.

### The Data Layer

>The data layer allows retrieving and persisting application data through Repositories* and Data Sources, which can be local with a database or remote with an API.

>*In practice, the "repository pattern" should be used to manage different data sources (DB, API, Cache), otherwise it is implemented to support "offline mode".

### The Presentation Layer

>The UI's role is to display the application's data on the screen and serve as the main point of user interaction. The used pattern is MVVM (Model - View - ViewModel) / UDF.

<p align="center">
<img src="https://github.com/selmanon/composeCleanArch/assets/2206036/6d5d69e3-8a1b-4ff0-ac7d-ccd5e1df9fad" alt="drawing" width="700"/>
</p>

## Application Rendering

<p align="center">
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/home.png" width="250" title="Home screen">
  &nbsp; &nbsp; &nbsp; 
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/post_screen.png" width="250" alt="Post screen">
  &nbsp; &nbsp; &nbsp; 
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/error.png" width="250" alt="Error screen">
</p>

## Areas for Improvement:

### Testing

- Unit tests for ViewModel. [apparently a `ViewModel` using _paging_ can only be tested on the UI side](https://developer.android.com/topic/libraries/architecture/paging/test)
- Inject scope and dispatcher to facilitate testing. ✅
- Unit tests for PagingSource. ✅
- User interface tests.

### Architecture

- Support "offline first" mode.
   -  Add Room persistence library.
   -  Implement RemoteMediator.
- Have a module for each layer. ✅

### UI

- Add a "retry" button on the data loading error screen. ✅
- Add pull-to-refresh. ✅
- Scroll management. ✅
- Replace error message _text_ with a _snack bar_.
- Add navigation in the AppBar. ✅
- Add an "up" button to scroll to the top of the list. ✅
- Filter the list of posts based on a tag. ✅

### Refactoring

- Remove the "material" dependency (as it is only used for "pull-to-refresh") and use _Material3_. ✅
- Improve error handling (via _Retrofit_ `CallAdapter`). ✅
    - Use a `sealed class` to distinguish between an _exception_ and an _error_. ✅
- Add a buildSrc module for version management (Android/release). ✅
- Add documentation.

### CI/CD

- Configure CI via GitHub Actions:
   - Code formatting with Spotless/Ktlint. ✅
   - Unit test coverage via Jacoco.
   - Generate the release.

### [Bugs/Regression]

- _Threading_ error: <code>(Skipped 78 frames!)</code> ✅
- Scroll position is reset to the top when navigating back from "Post" screen to "Home" screen (hint: after adding the search by tag feature).
