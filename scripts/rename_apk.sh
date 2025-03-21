#!/bin/bash

# Get version name from build.gradle
VERSION_NAME=$(grep -oP 'versionName "\K[^"]+' app/build.gradle)

# Create version-specific APK name
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
NEW_NAME="app/build/outputs/apk/debug/game-mod-menu-v${VERSION_NAME}.apk"

# Copy the APK with the new name
cp "${APK_PATH}" "${NEW_NAME}"

echo "APK renamed to $(basename ${NEW_NAME})"
echo "Files available:"
find app/build/outputs -name "*.apk"