#!/bin/bash

# This script generates a changelog based on git commits since the last tag
# It's used in the GitHub Actions workflow to create release notes

# Get the current tag
CURRENT_TAG=$1

# If no tag is provided, use the most recent one
if [ -z "$CURRENT_TAG" ]; then
  CURRENT_TAG=$(git describe --tags --abbrev=0)
fi

# Get the previous tag
PREVIOUS_TAG=$(git describe --tags --abbrev=0 HEAD~1 2>/dev/null)

# If there is no previous tag, get all commits
if [ -z "$PREVIOUS_TAG" ]; then
  echo "## What's New in $CURRENT_TAG"
  echo ""
  git log --pretty=format:"- %s" | head -10
else
  echo "## What's New in $CURRENT_TAG"
  echo ""
  echo "Changes since $PREVIOUS_TAG:"
  echo ""
  git log --pretty=format:"- %s" $PREVIOUS_TAG..$CURRENT_TAG
fi

# Add download links
echo ""
echo "## Downloads"
echo ""
echo "- [Download Standard APK](app-debug.apk)"
VERSION_NAME=$(grep -oP 'versionName "\K[^"]+' app/build.gradle)
echo "- [Download Versioned APK](game-mod-menu-v${VERSION_NAME}.apk)"