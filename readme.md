# Monarch <sup>*beta*</sup> 
![Версия релиза](https://img.shields.io/github/v/release/NodesLab/Monarch?style=flat-square)
![Кол-во загрузок](https://img.shields.io/github/downloads/NodesLab/Monarch/total?style=flat-square)

> Многофункциональный ассистент

## Описание

__Monarch__ (*mon*olithic *arch*itecture) — Мобильный ассистент с командным интерфейсом
реализованным в виде чата для выполнения различных задач, построенный на основе
[Jetpack Compose](https://developer.android.com/jetpack/compose).

Проект состоит из двух компонентов:

1. [Ядро](app/src/main/java/net/monarch/app/core) - функциональная часть (команды).
2. [UI](app/src/main/java/net/monarch/app/ui) - пользовательский интерфейс.

## Скриншоты

<p align="center">
  <img src="docs/screenshot_1.png" alt="Снимок экрана 2" width="25%" height="25%">
  <img src="docs/screenshot_2.png" alt="Снимок экрана 2" width="25%" height="25%">
  <img src="docs/screenshot_3.png" alt="Снимок экрана 2" width="25%" height="25%">
</p>

## Функционал

> **Примечание**
>
> Голосовой ввод на данный момент не работает!

Базовые команды:

- `/aliases` - Список алиасов команд.
- `/commands` - Список доступных команд.

Шифрование:

- `/base64` - Конвертация текста в base64 и обратно.

Работа с сетью:

- `/ipinfo` - Получение информации об IP.
- `/port` - Проверка доступности порта.

Работа с текстом:

- `/genstr` - Генерация строки случайных символов.
- `/hash` - Хеширование строки через разные алгоритмы (SHA-512, SHA-384, SHA-256, SHA-1, MD5).

## Зависимости

- Android 10
- JDK 11 *(только для сборки)*
- Gradle 7.5 *(только для сборки)*
- Git 2.37 *(только для сборки)*

*Дополнительные зависимости указаны в [`app/build.gradle.kts`](app/build.gradle.kts) и 
[`build.gradle.kts`](build.gradle.kts).*

## Установка

*TODO*

## Дополнительная информация

- [TODO-список проекта](todo.md)

## Лицензия

Copyright © 2022 [Node](https://github/TheNodeOrg).

Проект распространяется под лицензией [Apache License 2.0](license).
