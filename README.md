# App
This app provides information on crypto currencies in the market. There are basically 2 screens 
in the app - a list screen that displays top 10 crypto currencies and a details screen that displays
the details related to that particular selected crypto currency.

## Technical Details

### Architectural Pattern
The architectural pattern used is MVI redux architecture. The app contains several modules which are 
classified based on feature, domain and data layers. There are also common modules that are re-used
in several other modules.

### Modules
1) Feature Module

View - Contains compose code
ViewModel - Responsible for managing the state and business logic. Sends event which the reducer will
            reduce into state and effect which is observed by the view
ViewState - Represents the UI state 
ViewEffect - Handles side effects such as showing message, navigation etc
ViewEvent - Handles various events triggered by the view model
Reducer - Takes state and event and reduces it into state and effect

2) Domain Module

Contains Model class and a use case which is responsible for performing only one task. The use case
maps the entity to the model and provides it to the view model. 

3) Data Module

- Contains the API interface, Room database dao interface
- Entity that is provided by the API or the Room database
- Repository that decides if it needs to fetch data either from the API or the Room database
- The repository provides the entity to the domain layer

4) Common UI Module

Contains UI components, resources such as drawables, strings, reusable UI code, color, theme 
and typography

5) Navigation Module

Contains the routes for the screens

6) Feature base Module

Contains BaseViewModel and Reducer

7) Core base Module

Contains Internet util and AppResult class which is a generic implementation for success and 
error case used by domain and data modules

8) Common utils Module

Contains error type enum for handling the error codes and also extension class

### Tech Stack
- Android Studio
- Kotlin
- Hilt DI
- Room Database
- Jetpack Compose
- Retrofit
- Coroutines
- JUnit 5
- Mockito/Mockk
- Material3
