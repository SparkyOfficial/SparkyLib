# SparkyLib

## English

SparkyLib is a set of modules for convenient game logic development, UI, event system, task scheduler, particles, ECS, and network RPC, so that the same game code (logic, data, scripts) easily works in both Minecraft server and LibGDX client.

### Concept

The idea is to have shared game logic with platform abstractions, where platform adapters implement the details (particle rendering, entity spawning, task scheduler, etc.).

### Main Features

- **Core** (kotlin-first): utilities, collections, configuration, serialization (JSON/YAML), DI provider (lightweight), logging adapter
- **Events**: high-performance event system (sync/async), listeners with priorities
- **Scheduler / Task system**: unified interface for task scheduling; adapters for Bukkit scheduler and LibGDX render thread / timer
- **ECS** (Entity-Component-System): cache-efficient C++-style implementation on Kotlin/Java (arrow containers) for game logic
- **Packet / RPC**: simple RPC over WebSocket/TCP for client-server synchronization
- **DataStore**: storage abstraction (JSON, YAML, SQLite) with adaptable backends and unified API
- **Particles & VFX Bridge**: declarative effect description, with implementation for LibGDX and Minecraft (conversion to Particle/Packet)
- **Commands**: DSL for command registration with advanced features (permissions, aliases, validation) for plugin and local console
- **UI toolkit** (mini): declarative UI-DSL for LibGDX + simplified interfaces for Minecraft GUIs (Inventory wrappers)
- **Asset loader**: unified interface for loading resources (png, json, models, sounds, scripts) with cache and flexible loading system
- **Kotlin Scripting** (optional): safe kts-scripts for modding/plugins
- **Platform adapters**: bukkit/paper adapter, fabric/forge adapter, libgdx adapter — implement platform APIs

---

## Russian

SparkyLib - это набор модулей для удобной разработки игровой логики, UI, системы событий, задач, частиц, ECS и сетевого RPC, так чтобы один и тот же игровой код (логика, данные, скрипты) легко работал в Minecraft-сервере и в LibGDX-клиенте.

### Концепция

Идея: общая игровая логика + абстракции платформенных вещей → платформенные адаптеры реализуют детали (рендер частиц, спавн энтити, планировщик задач и т.д.).

### Главные возможности

- **Core** (kotlin-first): утилиты, коллекции, конфигурация, сериализация (JSON/YAML), DI-провайдер (легковесный), logging adapter
- **Events**: высокопроизводительная система событий (sync/async), слушатели с приоритетами
- **Scheduler / Task system**: единый интерфейс для планирования задач; адаптеры для Bukkit scheduler и LibGDX render thread / timer
- **ECS** (Entity-Component-System): кэш-эффективная С++-style реализация на Kotlin/Java (арроу-контейнеры) для игровой логики
- **Packet / RPC**: простое RPC по WebSocket/TCP для клиент-серверной синхронизации
- **DataStore**: абстракция хранения (JSON, YAML, SQLite) с адаптируемыми бекендами и единым API
- **Particles & VFX Bridge**: декларативное описание эффектов, с реализацией для LibGDX и для Minecraft (переобразование в Particle/Packet)
- **Commands**: DSL для регистрации команд с расширенными возможностями (разрешения, псевдонимы, проверка) для плагина и для локальной консоли
- **UI toolkit** (мини): декларативный UI-DSL для LibGDX + упрощённые интерфейсы для Minecraft GUIs (Inventory wrappers)
- **Asset loader**: унифицированный интерфейс загрузки ресурсов (png, json, models, sounds, scripts) с кэшем и гибкой системой загрузки
- **Kotlin Scripting** (optional): безопасные kts-скрипты для моддинга/плагинов
- **Platform adapters**: bukkit/paper adapter, fabric/forge adapter, libgdx adapter — реализуют платформенные API

---

## Ukrainian

SparkyLib — набір модулів для зручної розробки ігрової логіки, UI, системи подій, завдань, частинок, ECS та мережевого RPC, так щоб той самий ігровий код (логіка, дані, скрипти) легко працював у сервері Minecraft і клієнті LibGDX.

### Концепція

Ідея: загальна ігрова логіка + абстракції платформених речей → платформені адаптери реалізують деталі (рендер частинок, спавн ентіті, планувальник завдань тощо).

### Головні можливості

- **Core** (kotlin-first): утиліти, колекції, конфігурація, серіалізація (JSON/YAML), DI-провайдер (легковажний), logging adapter
- **Events**: високопродуктивна система подій (sync/async), слухачі з пріоритетами
- **Scheduler / Task system**: єдиний інтерфейс для планування завдань; адаптери для Bukkit scheduler і LibGDX render thread / timer
- **ECS** (Entity-Component-System): кеш-ефективна С++-style реалізація на Kotlin/Java (арроу-контейнери) для ігрової логіки
- **Packet / RPC**: просте RPC по WebSocket/TCP для клієнт-серверної синхронізації
- **DataStore**: абстракція зберігання (JSON, YAML, SQLite) з адаптованими бекендами та єдиним API
- **Particles & VFX Bridge**: декларативний опис ефектів, з реалізацією для LibGDX і для Minecraft (перетворення в Particle/Packet)
- **Commands**: DSL для реєстрації команд з розширеними можливостями (дозволи, псевдоніми, перевірка) для плагіна і для локальної консолі
- **UI toolkit** (міні): декларативний UI-DSL для LibGDX + спрощені інтерфейси для Minecraft GUIs (Inventory wrappers)
- **Asset loader**: уніфікований інтерфейс завантаження ресурсів (png, json, models, sounds, scripts) з кешем та гнучкою системою завантаження
- **Kotlin Scripting** (опціонально): безпечні kts-скрипти для моддингу/плагінів
- **Platform adapters**: bukkit/paper adapter, fabric/forge adapter, libgdx adapter — реалізують платформені API

---

## Author

Created by Андрій Будильников (Sparky)