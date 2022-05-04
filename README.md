# Before you start reading anything else
I want to say that this test was a challenge for me, although I have experience with architectures, views, DI, 
network, etc, it was my first time implementing something with Canvas and Coordinates management(I only knew the 
concept 😅), I had to read/test a LOT to reach the solution proposed here.

I really enjoyed it, thank you.

PD: Hope to receive good feedback 😬

# Peaks Test 🎉🎉
Peaks Test is an app with a single screen where the user can drag rectangles.

The app runs from API 21 and above, just clone the project and run it as you normally run an Android project.

## ⚙️ Architecture
The architecture used for the application consists of the following:

 - Clean Architecture.
 - MVVM pattern.
 - Android Architecture components([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata))

We don't have a real API, so we implemented the whole structure of the data package as it should be done but we also added the implementation of a `PeaksRepositoryMock` which returns fake data for app use.

in the `domainModule` file is where the repository change is done depending (in this case) on the buildType.

## 📚 Tech stack

  - [Retrofit 2](https://square.github.io/retrofit/): type-safe client for Android and Java/Kotlin, used to make API REST calls.
  - [Koin](https://insert-koin.io/): A smart Kotlin injection library .
  - [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines): Simple library used to run Asynchronous or non-blocking calls.
  - [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization): Simple library used to serialize data, 
    in this case I've used to serialize the info saved in SharedPreferences.
  - [Mockk](https://github.com/mockk/mockk): Open source library focused on making mocking test more easily.
  - [Kluent](https://github.com/MarkusAmshove/Kluent): Fluent Assertion-Library for Kotlin.
  - [Material components](https://material.io/develop/android/docs/getting-started): interactive building blocks for creating a user interface and animations between views.
  - [Jetpack](https://developer.android.com/jetpack):
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

## 🧱 Project Structure

```sh
.
├── data
│   ├── mapper
│   ├── model
│   ├── repository
│   ├── service
│   └── utils
├── di
├── domain
│   ├── model
│   ├── repository
│   └── usecase
├── presentation
│   └── view
└── utils
```

### Data Package
- `mappers` as the name suggests, this folder contains the required files to pass the info from the API response to the use-cases models in the project.
- `model` data class files for the API call response.
- `repository` class implementation for each interface call declared in Domain package.
- `service` representation for the API Network client call.
- `utils` contains a Result Wrapper(CallResult), Exception file and extension used to process the Retrofit response.

### Domain Package
- `model` data class files for the API call response as we need it in the app.
- `repository` interface that define the use case to be performed by data package
- `usecases` single purpose class to encapsulate the logic for a API Call.

### Presentation Package
- `view` custom view implemented to draw a rectangle in a position.
- it also contains the `MainActivity` and `MainViewModel`.

### App Package
- `di` all the classes related to dependency injection(`appModule`, `dataModule`, `domainModule`).

### 🎥 Video

https://user-images.githubusercontent.com/10689052/166815844-d25c6860-07ca-47a7-86c7-3eef8307c644.mov


### 📓 Notes and considerations

  - This project includes testing of:
    - Repository
    - Mappers
    - UseCases
    - ViewModel
    - UI(with Espresso), just a validate content test 😅
  - I normally use Koin for DI, but I also have experience with hilt.
  - App logo taken from Peaks [website](https://www.peaks.com/)
