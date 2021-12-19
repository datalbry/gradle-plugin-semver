# Gradle SemVer

The `Gradle Semantic Versioning` is a sophisticated gradle plugin, helping developers to update their versions of gradle builds,
by processing through the git history.

## Getting Started

Setting up the plugin requires the following steps:
 
1. Add the Plugin to your `build.gradle.kts`
    ```kotlin
    plugins {
      id("io.datalbry.plugin.semver") version "<version>"
    }
    ```
2. The version can now be updated using `./gradlew updateReleaseVersion`

### Setup Pre Releases

The Plugin supports adding multiple version schemes, 
this might be especially useful for alpha and beta releases.

1. Add the Pre Release Version in the `build.gradle.kts`
    ```kotlin
    semver {
        ...   
        version("snapshot", "SNAPSHOT")
        version("alpha", "alpha.{COMMIT_TIMESTAMP}")
    }
    ```

2. The version can now be updated using:
  a. `./gradlew updateSnapshotVersion` for snapshot version
  b. `./gradlew updateAlphaVersion` for alpha version
   
> **NOTE:** The latest versions can be found [here](https://github.com/datalbry/gradle-semver-plugin/tags).
   
### Configuration

The Plugin is highly configurable. The following parameters can be set using either the extension or can be passed as parameters.

|Parameter|Description|Value|Default|
|---------|-----------|-----|-------|
|`semver.propertiesFile`|The location of the properties file to write the version property to|`String`|`./gradle.properties`|
|`semver.version`|Adds a new version, such as SNAPSHOT, Alpha or Beta to the plugin. Versions are completely configurable.|`(String, String)`||

#### Version Template
The version templates of the semver plugin MUST fulfill the SemVer standard. 
Besides that, we support the following placeholder:

| Placeholder | Substituion |
|-------------|-------------|
|`{COMMIT_TIMESTAMP}`|Will be substituted with the timestamp of the latest commit (epoch millis). |
|`{BUILD_TIMESTAMP}`|Will be substituted with the current timestamp (epoch millis). |
  

## License
>Copyright 2021 DataLbry Technologies UG
>
>Licensed under the Apache License, Version 2.0 (the "License");
>you may not use this file except in compliance with the License.
>You may obtain a copy of the License at
>
>http://www.apache.org/licenses/LICENSE-2.0
>
>Unless required by applicable law or agreed to in writing, software
>distributed under the License is distributed on an "AS IS" BASIS,
>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>See the License for the specific language governing permissions and
>limitations under the License.
