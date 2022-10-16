# Helio <sup>alpha</sup> ![Release](https://img.shields.io/github/v/release/hepller/HelioApp)

> Многофункциональный ассистент

## Описание

__Helio__ — Мобильный ассистент с чато-подобным интерфейсом для выполнения различных задач, построенный на основе
[Kotlin](https://kotlinlang.org/) и [Jetpack Compose](https://developer.android.com/jetpack/compose)

## Функционал

Базовые команды:
- `/aliases` - Список алиасов команд
- `/commands` - Список доступных команд

Работа с сетью:
- `/ipinfo` - Получение информации об IP
- `/port` - Проверка доступности порта

Работа с текстом:
- `/base64` - Конвертация текста в base64 и обратно
- `/genstr` - Генерация строки случайных символов
- `/hash` - Хеширование строки через разные алгоритмы (sha-512, sha-384, sha-256, sha-1, md5)

## Зависимости

- Android 10
- JDK 11 *(только для сборки)*
- Gradle 7.5 *(только для сборки)*
- Git 2.37 *(только для сборки)*

*Дополнительные зависимости указаны в [`app/build.gradle.kts`](app/build.gradle.kts) и [`build.gradle.kts`](build.gradle.kts)*

## Установка и запуск

__TODO__

## Лицензия

Copyright © 2022 [The Helio contributors](contributors.md)

Проект распространяется под лицензией [Apache License 2.0](license)