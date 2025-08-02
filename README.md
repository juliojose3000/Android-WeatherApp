# 🌦️ WeatherApp - Android Weather Application

[![Android CI](https://img.shields.io/badge/Android-CI-brightgreen.svg)](https://github.com/your-repo/WeatherApp)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![Kotlin](https://img.shields.io/badge/kotlin-1.8.0-blue.svg)](https://kotlinlang.org)

## 📱 Overview
WeatherApp is a modern Android application that provides real-time weather information based on the user's current location. The app follows clean architecture principles and implements best practices in Android development.

## 🚀 Features
- Real-time weather data
- Location-based weather updates
- Offline data support
- Geocoding for location addresses
- Permission handling system
- Clean architecture implementation
- Responsive UI with loading states

## 🛠️ Technology Stack
- **Language**: Java
- **Architecture**: Clean Architecture (Domain, Data, Presentation layers)
- **Dependency Injection**: Hilt
- **Data Binding**: Android Data Binding
- **Network**: Internet connectivity handling
- **Location Services**: Android Location API
- **Geocoding**: Android Geocoder API

## 📋 Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 21 or higher
- Android Build Tools
- JDK 11 or higher

## ⚙️ Installation & Setup
1. Clone the repository:
```bash
git clone https://github.com/your-username/WeatherApp.git
```

2. Open the project in Android Studio

3. Add required API keys in `local.properties`:
```properties
# Add your API keys here if needed
```

4. Sync project with Gradle files

5. Run the application

## 🏗️ Project Structure
```
├── app/                  # Application module
├── core/                 # Core functionality module
├── data/                # Data layer implementation
├── domain/              # Domain layer with business logic
└── presentation/        # UI layer with fragments and viewmodels
```

## 🔧 Configuration
The application requires the following permissions:
- `android.permission.INTERNET`
- `android.permission.ACCESS_FINE_LOCATION`
- `android.permission.ACCESS_COARSE_LOCATION`

## 📱 Running the Application
1. Enable location services on your device
2. Grant location permissions when prompted
3. The app will automatically fetch weather data for your current location

## 🧪 Testing
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## 🤖 AI Tools Used

**Claude:**
- Building WeatherBackgroundManager file
- Generation of vector drawables like floating_clouds.xml, fog_overlay, etc
- Generating testcases (not all)
- Generation of MD file for Github repository

**Gemini (Android Studio tool):**
- Creation of strong commit messages

**ChatGPT:**
- Questions like to analyze the error in a function that maybe wasn't clear in a first sight

## 🏛️ Design Patterns Used

### Architectural
- **MVVM**: To separate the logic from view

### Behavioral
- **Use Case**: to encapsulate business logic as a single action
- **Observer**: implemented with LiveData to react when data is fetched from API. Also to dismiss or show a loader view

### Creational
- **Singleton**: implemented to ensure just one instance exists with Room database and Retrofit client using Hilt

### Structural
- **DTO**: used to transfer data between API to mobile app
- **DAO**: to separate the data persistence logic
- **Repository**: to abstract data sources (local and remote)

## 🤝 Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📸 Screenshots

### Cloudy
<img width="540" height="1200" alt="image" src="https://github.com/user-attachments/assets/29954c8b-0952-4f03-8bdc-b0cb39c37118" />

### Sunny
<img width="540" height="1200" alt="image" src="https://github.com/user-attachments/assets/df7aa42d-421c-488c-bd5d-4f504f788a37" />

### Rainy
<img width="540" height="1200" alt="image" src="https://github.com/user-attachments/assets/cc8494cc-206d-4bb0-9688-3915adb24b67" />

### Snowy
<img width="540" height="1200" alt="image" src="https://github.com/user-attachments/assets/b112f36e-0e72-4ed9-b2c0-ea8b3cedd0de" />

### Stormy
![Screen_recording_20250801_220649-ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/c5bb4002-a8e7-415d-9ccc-2956b172a1b5)

### Foggy
<img width="540" height="1200" alt="image" src="https://github.com/user-attachments/assets/7ffffcf8-297d-42bb-89e4-c6cc2b4762cd" />

---

**Note**: This app uses clean architecture principles with a modular approach, separating concerns into core, data, domain, and presentation layers. The application handles location permissions gracefully and implements proper error handling for various scenarios including network connectivity issues.
