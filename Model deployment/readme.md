# LiteGuard Medical AI - Deployment Guide

Quick deployment instructions for the LiteGuard Android application.

**Source Code**: [https://github.com/TSTS09/LiteGuard/tree/main](https://github.com/TSTS09/LiteGuard/tree/main)

## Prerequisites

- Android Studio Arctic Fox or later
- Android SDK (API 24+, target 34)
- Android device with 4GB+ RAM
- 1-4GB free storage space

## Quick Deploy

### 1. Clone & Open
```bash
git clone https://github.com/TSTS09/LiteGuard.git
cd LiteGuard/Model\ deployment
```
Open the `Model deployment` folder in Android Studio.

### 2. Configure Authentication (Optional)
For authenticated models, create `local.properties`:
```properties
HF_ACCESS_TOKEN=your_hugging_face_token
```
Get token from [Hugging Face Settings](https://huggingface.co/settings/tokens).

### 3. Build & Install

**Via Android Studio:**
1. Connect Android device (USB debugging enabled)
2. Click Run button
3. Select your device

**Via Command Line:**
```bash
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Device Requirements

- **Android**: 7.0+ (API 24)
- **RAM**: 4GB minimum, 6GB+ recommended
- **Storage**: 1-4GB depending on model
- **Architecture**: ARM64 or x86_64

## Model Options

| Model | Size | Auth Required | Best For |
|-------|------|---------------|----------|
| Gemma3-1B | 500MB | Yes | Low-end devices |
| DeepSeek-R1-Distill | 1GB | No | Balanced performance |
| LiteGuard-merged-Phi2 | 2GB | No | Better accuracy |
| Phi-4-mini-instruct | 3GB | No | High-end devices |

## First Run

1. Select model based on device capabilities
2. Authenticate with Hugging Face (if required)
3. Accept license terms
4. Wait for model download
5. Start chatting

## Troubleshooting

**Download fails**: Check internet, verify token, ensure storage space
**Out of memory**: Use smaller model, close other apps, restart device
**Auth issues**: Verify token validity, clear app data if needed

## Build Variants

```bash
# Debug build
./gradlew assembleDebug

# Release build  
./gradlew assembleRelease

# Install directly
./gradlew installDebug
```

---

**Note**: For comprehensive documentation, see the main README in the repository root.
