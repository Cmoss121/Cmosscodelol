#!/bin/bash

# This script is used to easily create a new version release
# It updates the version numbers in build.gradle and creates a Git tag

# Check if a version number was provided
if [ $# -eq 0 ]; then
  echo "Usage: ./scripts/create_release.sh <version_number>"
  echo "Example: ./scripts/create_release.sh 1.6"
  exit 1
fi

VERSION=$1
# Calculate version code based on version number (remove dots and multiply by 10)
VERSION_CODE=$(echo $VERSION | sed 's/\.//g')
VERSION_CODE=$((VERSION_CODE * 10))

# Update build.gradle with new version
sed -i "s/versionCode [0-9]*/versionCode $VERSION_CODE/" app/build.gradle
sed -i "s/versionName \"[0-9.]*\"/versionName \"$VERSION\"/" app/build.gradle

echo "Version updated to $VERSION (code: $VERSION_CODE) in build.gradle"

# Create tag
git add app/build.gradle
git commit -m "Release version $VERSION"
git tag -a "v$VERSION" -m "Release version $VERSION"

# Check if remote repository is configured
if git remote | grep -q "origin"; then
  # Remote exists, try to push
  git push && git push --tags
  echo "Version $VERSION tag created and pushed!"
  echo "GitHub Actions workflow is now building and publishing the release."
  echo "Check your GitHub repository's Actions tab for progress."
else
  echo "No remote repository configured. Tag created locally only."
  echo "To push later, configure a remote repository with:"
  echo "    git remote add origin <repository-url>"
  echo "Then push with:"
  echo "    git push origin main && git push origin --tags"
  echo "After pushing, GitHub Actions will automatically build and publish the release."
fi