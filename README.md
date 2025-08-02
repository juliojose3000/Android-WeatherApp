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
- **Navigation**: AndroidX Navigation
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

## 🤝 Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## 📸 Screenshots
[Add screenshots here]

---

**Note**: This app uses clean architecture principles with a modular approach, separating concerns into core, data, domain, and presentation layers. The application handles location permissions gracefully and implements proper error handling for various scenarios including network connectivity issues.