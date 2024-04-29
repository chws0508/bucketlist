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
- Architecture:
  - Clean Architecture (Presentation - Domian - Data): Domain module is a pure Kotlin module with no Android dependencies. Data and Presentation modules are depend on Domain module. Core business logic is focused on Domain module. 
  - MVVM Architecture (View - ViewModel - Model): Facilitates separation of concerns and promotes maintainability.
  - Repository Pattern: Acts as a mediator between different data sources and the application's business logic.
- [Hilt](https://dagger.dev/hilt/) - Dependency injection.
- [landScapist](https://coil-kt.github.io/coil/) - An image loading library for compose by skydoves.
- [Mockk](https://github.com/mockk/mockk) - mockking Library For Kotlin to test
- [Turbine](https://github.com/cashapp/turbine) - testing library for kotlinx.coroutines Flow.
- [Truth](https://github.com/google/truth) - Fluent assertions for Java and Android

## Features

> ### Bucket List 

| Switch Sort Option | Add Bucket | Edit/Delete Bucket |
| :---------------: | :---------------: | :---------------: |
| <img src="https://github.com/chws0508/bucketlist/blob/main/demo/switch_sort_option.gif" align="center" width="300px"/> | <img src="https://github.com/chws0508/bucketlist/blob/main/demo/add_bucket.gif" align="center" width="300px"/> | <img src="https://github.com/chws0508/bucketlist/blob/main/demo/edit_delete_bucket.gif" align="center" width="300px"/> |

> ### Bucket Records

| Switch Categegory | Add Record from bucket | Edit/Delete Record From Bucket |
| :---------------: | :---------------: | :---------------: |
| <img src="https://github.com/chws0508/bucketlist/blob/main/demo/switch_record_category.gif" align="center" width="300px"/> | <img src="https://github.com/chws0508/bucketlist/blob/main/demo/add_record.gif" align="center" width="300px"/> | <img src="https://github.com/chws0508/bucketlist/blob/main/demo/edit_delete_record.gif" align="center" width="300px"/> |

> ### Theme

<div align="center">

| Change Dark Themes by System Default  | Dark / Light  |
| :---------------: | :---------------: |
| <img src="https://github.com/chws0508/bucketlist/blob/main/demo/system_default.gif" align="center" width="300px"/> | <img src="https://github.com/chws0508/bucketlist/blob/main/demo/dark_light.gif" align="center" width="300px"/> |

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
