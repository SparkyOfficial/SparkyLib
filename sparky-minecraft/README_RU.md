# Адаптер SparkyLib для Minecraft

## Обзор
Адаптер SparkyLib для Minecraft - это специализированная библиотека, которая расширяет основной фреймворк SparkyLib для обеспечения бесшовной интеграции с серверами Minecraft (Bukkit/Spigot). Он использует архитектуру Entity-Component-System (ECS) для создания мощного и гибкого фреймворка для разработки плагинов Minecraft.

## Возможности

### Компоненты ECS для Minecraft
- **BlockComponent** - Представляет блоки Minecraft с позицией, типом и информацией о мире
- **PlayerComponent** - Управляет состоянием игрока, включая здоровье, уровень еды, опыт и статус онлайн
- **ItemComponent** - Обрабатывает предметы Minecraft с типом, количеством, прочностью, отображаемым именем и описанием

### Системы ECS для Minecraft
- **BlockSystem** - Управляет манипуляциями с блоками и взаимодействием с миром
- **PlayerSystem** - Обрабатывает обновления состояния игроков и управление ими
- **ItemSystem** - Управляет взаимодействием с предметами и операциями инвентаря

### Функции интеграции
- Бесшовная интеграция с API Bukkit/Spigot
- Архитектура на основе событий с использованием EventBus SparkyLib
- Интеграция планировщика для операций по времени
- Система эффектов частиц
- Обработка команд с разрешениями
- Управление активами для ресурсов

## Установка

Добавьте следующую зависимость в ваш `pom.xml`:

```xml
<dependency>
    <groupId>com.sparky</groupId>
    <artifactId>sparky-minecraft</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Пример использования

```java
// Инициализация адаптера Minecraft
MinecraftAdapter minecraftAdapter = new MinecraftAdapter(yourPlugin);

// Создание сущности игрока
Entity playerEntity = minecraftAdapter.getEntityManager().createEntity();
PlayerComponent playerComponent = new PlayerComponent(
    "Steve", 
    "player-uuid", 
    20, 20, 15, 10, 0.5f, true
);
playerEntity.addComponent(playerComponent);

// Создание сущности блока
Entity blockEntity = minecraftAdapter.getEntityManager().createEntity();
BlockComponent blockComponent = new BlockComponent(
    "diamond_ore", 
    50, 64, 50, 
    "world"
);
blockEntity.addComponent(blockComponent);

// Системы автоматически обработают эти сущности
minecraftAdapter.update();
```

## Архитектура

Адаптер Minecraft следует той же архитектуре ECS, что и основной SparkyLib:

1. **Сущности** - Игровые объекты, которые содержат компоненты
2. **Компоненты** - Контейнеры данных, описывающие аспекты сущности
3. **Системы** - Процессоры логики, которые работают с сущностями с определенными компонентами

Эта архитектура обеспечивает:
- Высокую производительность благодаря ориентации на данные
- Легкую расширяемость путем добавления новых компонентов и систем
- Четкое разделение ответственности
- Гибкую композицию сущностей

## Требования
- Java 17 или выше
- Bukkit/Spigot 1.19.4 или совместимый
- Основные модули SparkyLib

## Лицензия
Этот проект лицензирован по лицензии MIT - смотрите файл LICENSE для получения подробной информации.