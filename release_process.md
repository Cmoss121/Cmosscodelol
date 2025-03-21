# Automated Release Process

This document explains how to use the automated release process for the Mod Menu application.

## Overview

Our project uses GitHub Actions to automatically build and publish APKs. When you push a new version tag, GitHub Actions will:

1. Build the APK
2. Rename it to include the version number
3. Generate a changelog from commit messages
4. Create a GitHub release with both APKs attached

## Release Steps

### Option 1: Using the Helper Script (Recommended)

We've created a helper script that makes releasing a new version easy:

```bash
./scripts/create_release.sh <version_number>
```

For example, to release version 1.8:

```bash
./scripts/create_release.sh 1.8
```

This will:
- Update the version in build.gradle
- Commit the changes
- Create a tag
- Push everything to GitHub
- Trigger the GitHub Actions workflow

### Option 2: Manual Process

If you prefer to do it manually:

1. Update the `versionCode` and `versionName` in `app/build.gradle`
2. Commit the changes:
   ```bash
   git add app/build.gradle
   git commit -m "Bump version to X.Y"
   ```
3. Create a tag:
   ```bash
   git tag -a vX.Y -m "Release version X.Y"
   ```
4. Push the changes and tag:
   ```bash
   git push && git push --tags
   ```

## Monitoring the Release

After pushing a tag:

1. Go to your GitHub repository
2. Click on "Actions" tab
3. You should see the "Build and Release APK" workflow running
4. Once complete, go to the "Releases" section to see your new release

## Troubleshooting

If the release process fails:

1. Check the workflow logs in GitHub Actions
2. Verify your GitHub Personal Access Token has the correct permissions
3. Ensure the scripts have execute permissions (`chmod +x scripts/*.sh`)

For additional help, see GitHub's documentation on [release workflows](https://docs.github.com/en/actions/guides/about-releases).