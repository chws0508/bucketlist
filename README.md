<h1 align="center">BucketList </h1>

<p align="center">
  <a href="https://kotlinlang.org"><img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-1.9.0-blueviolet.svg?style=flat"/></a>
  <a href="https://android-arsenal.com/api?level=26"><img alt="API" src="https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://developer.android.com/studio/releases/gradle-plugin"><img alt="AGP" src="https://img.shields.io/badge/AGP-8.2.2-blue?style=flat"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>

BucketList is an application that helps you to make bucket list and store completed list

## Tech stack & Open-source libraries

### Android

- Minimum SDK level 26
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- JetPack
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI related data that isn't destroyed on app rotations.
  - [Room](https://developer.android.com/training/data-storage/room) - Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Data storage solution that uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.
  - [Navigation](https://developer.android.com/develop/ui/compose/navigation?hl=ko) - Handles navigating between your app's destinations.
- [Hilt](https://dagger.dev/hilt/) - Dependency injection.
- [landScapist](https://coil-kt.github.io/coil/) - An image loading library for compose by skydoves.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Mockk](https://github.com/mockk/mockk) - mockking Library For Kotlin to test
- [Turbine](https://github.com/cashapp/turbine) - testing library for kotlinx.coroutines Flow.
- [Truth](https://github.com/google/truth) - Fluent assertions for Java and Android

## Features

> ### Bucket List 


> ### Themes

<div align="center">



> ### Favorites

<div align="center">


</div>

> ### Network Connectivity

<div align="center">

</div>

## Architecture

BucketList is based on the MVVM architecture and the Repository pattern.

<p align = 'center'>
<img width = '600' src = 'https://user-images.githubusercontent.com/39554623/184456867-195f5989-dc9a-4dea-8f35-41e1f11145ff.png'>
</p>

## License

```xml
Designed and developed by 2024 chws0508

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
