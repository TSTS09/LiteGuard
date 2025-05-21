
# LiteGuard Medical AI

An Android application that provides on-device medical AI assistance using lightweight language models optimized for mobile deployment.

## 🔗 Source Code
**GitHub Repository**: [https://github.com/TSTS09/LiteGuard/tree/main](https://github.com/TSTS09/LiteGuard/tree/main)

## 📋 Overview

LiteGuard Medical AI is a mobile application designed to run large language models locally on Android devices for medical consultation and assistance. The app leverages Google's MediaPipe LLM Inference library to provide secure, private AI interactions without requiring internet connectivity for inference.

### Key Features
- **On-device AI**: All inference runs locally on the device
- **Privacy-focused**: No data sent to external servers during chat
- **Multiple model options**: Choose from different model sizes (500MB to 3GB)
- **Medical specialization**: Models optimized for medical use cases
- **OAuth integration**: Secure authentication with Hugging Face
- **Modern UI**: Built with Jetpack Compose

## 🛠️ System Requirements

### Development Environment
- **Android Studio**: Arctic Fox (2020.3.1) or later
- **Java JDK**: Version 8 or higher
- **Android SDK**: API level 24 (Android 7.0) minimum, API level 34 target
- **Kotlin**: Version 1.9.22
- **Gradle**: Version 8.2

### Device Requirements
- **Android Version**: 7.0 (API level 24) or higher
- **RAM**: Minimum 4GB, recommended 6GB+ for larger models
- **Storage**: 1-4GB free space depending on model size
- **Architecture**: ARM64 or x86_64

## 📦 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/TSTS09/LiteGuard.git
cd LiteGuard/Model\ deployment
```

### 2. Configure Hugging Face Authentication (Optional)

If you want to use models that require authentication:

1. Create a `local.properties` file in the project root:
```properties
# Hugging Face access token for authenticated model downloads
HF_ACCESS_TOKEN=your_hugging_face_token_here
```

2. Get your token from [Hugging Face Settings](https://huggingface.co/settings/tokens)

### 3. Build the Application

#### Using Android Studio (Recommended)
1. Open Android Studio
2. Choose "Open an existing project"
3. Navigate to the `Model deployment` folder
4. Wait for Gradle sync to complete
5. Build → Make Project
6. Run on device or emulator

#### Using Command Line
```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

### 4. Deploy to Device

#### Via Android Studio
1. Connect your Android device via USB
2. Enable Developer Options and USB Debugging
3. Click the "Run" button in Android Studio

#### Via ADB
```bash
# Install the APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Or for release builds
adb install app/build/outputs/apk/release/app-release.apk
```

## 🚀 Usage Guide

### First Launch
1. **Model Selection**: Choose from available models based on your device's capabilities:
   - **LiteGuard 500MB**: Gemma3-1B (requires auth)
   - **LiteGuard 1GB**: DeepSeek-R1-Distill
   - **LiteGuard 2GB**: LiteGuard-merged-Phi2
   - **LiteGuard 3GB**: Phi-4-mini-instruct

2. **Authentication** (if required): Sign in with Hugging Face for authenticated models

3. **License Acknowledgment** (if required): Review and acknowledge model licenses

4. **Model Download**: The app will download the selected model (may take several minutes)

### Using the Chat Interface
1. **Start Conversation**: Type your medical question in the text field
2. **Send Message**: Tap the send button or press enter
3. **View Response**: The AI will generate responses locally on your device
4. **Token Management**: Monitor remaining context tokens in the top bar
5. **Clear Chat**: Use the refresh button to start a new conversation
6. **Close Session**: Use the close button to return to model selection

### Model Management
- Models are downloaded once and cached locally
- You can switch models by returning to the selection screen
- Clear app data to remove downloaded models and free storage

## 🏗️ Architecture

### Core Components

#### Model Management (`InferenceModel.kt`)
- Handles model loading and session management
- Manages MediaPipe LLM Inference engine
- Estimates token usage and context limits

#### Chat System
- **`ChatViewModel.kt`**: Manages chat state and coordinates inference
- **`ChatUiState.kt`**: Handles different model prompt formats
- **`ChatMessage.kt`**: Represents individual chat messages

#### Authentication (`LoginActivity.kt`, `OAuthCallbackActivity.kt`)
- OAuth 2.0 integration with Hugging Face
- PKCE (Proof Key for Code Exchange) for secure authentication
- Encrypted token storage using Android's security framework

#### UI Components
- **`MainActivity.kt`**: Main navigation and app structure
- **`ChatScreen.kt`**: Chat interface with Jetpack Compose
- **`SelectionScreen.kt`**: Model selection interface
- **`LoadingScreen.kt`**: Model download and loading states

### Dependencies
- **MediaPipe GenAI**: `com.google.mediapipe:tasks-genai:0.10.22`
- **Jetpack Compose**: Modern Android UI toolkit
- **AppAuth**: `net.openid:appauth:0.11.1` for OAuth
- **Security Crypto**: Encrypted SharedPreferences
- **OkHttp**: HTTP client for model downloads

## 🔧 Configuration

### Model Configuration (`Model.kt`)
Each model is configured with:
- **Path**: Local storage path for the model file
- **URL**: Download URL (Hugging Face repository)
- **License URL**: Link to model license terms
- **Authentication**: Whether OAuth is required
- **Backend**: Preferred inference backend (CPU/GPU)
- **Parameters**: Temperature, Top-K, Top-P for generation
- **UI State**: Prompt formatting for the specific model

### Build Configuration (`build.gradle.kts`)
- Target/Compile SDK versions
- Dependency versions
- ProGuard settings for release builds
- Build variants and signing configs

## 🛡️ Privacy & Security

### Data Protection
- **Local Processing**: All AI inference happens on-device
- **No Data Transmission**: Chat conversations never leave your device
- **Encrypted Storage**: Authentication tokens stored securely
- **Secure Authentication**: OAuth 2.0 with PKCE for API access

### Permissions
- **Internet**: Required only for model downloads and authentication
- **Storage**: To cache models locally

## 🐛 Troubleshooting

### Common Issues

#### Model Download Fails
- Check internet connection
- Verify Hugging Face token (if required)
- Ensure sufficient storage space
- Try clearing app data and re-downloading

#### Out of Memory Errors
- Use a smaller model variant
- Close other apps to free RAM
- Restart the device
- Consider devices with more RAM for larger models

#### Authentication Issues
- Verify Hugging Face token validity
- Check OAuth configuration in `AuthConfig.kt`
- Clear app data to reset authentication state

#### Performance Issues
- Use CPU backend for better compatibility
- Reduce context length by clearing chat history
- Monitor token usage to avoid context overflow

### Logs and Debugging
Enable developer options and use ADB to view logs:
```bash
adb logcat | grep -i "llminference\|mediapipe"
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Make your changes and test thoroughly
4. Commit with clear messages: `git commit -m "Add feature description"`
5. Push to your fork: `git push origin feature-name`
6. Create a Pull Request

### Development Guidelines
- Follow Android development best practices
- Use Kotlin coding conventions
- Write unit tests for new functionality
- Update documentation for significant changes
- Test on multiple devices and Android versions

## 📄 License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.

### Model Licenses
Each AI model has its own license terms:
- Check individual model repositories on Hugging Face
- License acknowledgment required during app setup
- Comply with model-specific usage restrictions

## 🔗 Links & Resources

- **Source Code**: [GitHub Repository](https://github.com/TSTS09/LiteGuard/tree/main)
- **MediaPipe Documentation**: [https://developers.google.com/mediapipe](https://developers.google.com/mediapipe)
- **Hugging Face Models**: [https://huggingface.co/models](https://huggingface.co/models)
- **Android Development**: [https://developer.android.com](https://developer.android.com)

## 📞 Support

For questions, issues, or contributions:
- Open an issue on the GitHub repository
- Check existing issues for solutions
- Provide detailed information including device specs and error logs

---

**Note**: This application is for educational and research purposes. Always consult qualified medical professionals for actual medical advice and treatment.
