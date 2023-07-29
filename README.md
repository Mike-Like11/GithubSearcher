# GithubSearcher

Android-приложение для поиска пользователей и просмотра содержимого репозиториев на Github
В роли источника данных для приложения – API https://api.github.com/.


## Функционал
Приложение содержит 2 экрана
1. На главном экране  отображается объединенный список карточек результата поиска по пользователям и репозиториям, отсортированный в лексикографическом порядке.При нажатии на карточку с пользователем открывается браузер устройства  с переходом на его страницу GitHub. При нажатии на карточку с репозиторием, открывается экран с содержимым репозитория.
2. Экран с содержимым репозитория в виде списка папок и файлов. При нажатии на папку, загружается ее содержимое (другие файлы и папки). При нажатии на файл, открывается WebView с его содержимым.


## Инструменты реализации

- Android Studio
- Kotlin
- Retrofit
- OkHttp
- Coroutines
- Glide
- xml верстка
- Koin
- Navigation Component
- Kotlinx-serialization

## Видеозапись работы приложения

https://github.com/Mike-Like11/GiphySearcher/assets/57439449/ecf26095-ff6d-42bd-9986-0b1857ab47c4