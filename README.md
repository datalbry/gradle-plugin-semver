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
2. Configure the Plugin
    ```kotlin
    semanticVersion {
        propertiesFile = "./gradle.properties"
    }
    ```

> **NOTE:** The latest versions can be found [here](https://github.com/datalbry/gradle-semver-plugin/tags).

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

